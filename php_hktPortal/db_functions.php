<?php

    require_once __DIR__ . '/db_connect.php';
    $db = new DB_CONNECT();
    $response = array();
 

    switch ($_POST['a_operation']) {
        case "login":
            login($_POST['sicil'], $_POST['sifre']);
            break;
        case "knnEkle":
            KnnEkle($_POST['sicil'], $_POST['tarih'], $_POST['yer'], $_POST['makno'], $_POST['aciklama']);
            break;
        case "makineListesi":
            makineListesiGetir();
            break;
        case "topXHata":
            topXHata($_POST['topX']);
            break;
        case "topCount":
            topCount();
            break;
}

function login($sicilno,$sif){
    
   if (!empty($sicilno) && !empty($sif)) {
    
       $result = mysql_query("SELECT * FROM kisiler WHERE sicil = $sicilno");
    
       if (!empty($result)) {
           if (mysql_num_rows($result) > 0) {
    
               $result = mysql_fetch_array($result);

                if($result["sifre"] == md5($sif)){

                    /*
                    $operator = array();
                    $operator["no"] = $result["no"];
                    $operator["sicil"] = $result["sicil"];
                    $operator["sifre"] = $result["sifre"];
                    $operator["makno"] = $result["makno"];
                    $operator["kaseno"] = $result["kaseno"];
                    $operator["adisoyadi"] = $result["adisoyadi"];
                    $operator["bolum"] = $result["bolum"];
                    $operator["gorev"] = $result["gorev"];
                    $operator["yalintakim"] = $result["sifre"];
                    $operator["operasyon"] = $result["operasyon"];
                    $operator["yetki"] = $result["yetki"];
                    $operator["isbaslamatarih"] = $result["isbaslamatarih"];
                    */

                    $response["success"] = 1;
                    //$response["operator"] = array();

                    //$response["operator"] = $operator;

                    //array_push($response["operator"], $operator);

                    echo json_encode($response);
                }
                else{
                    $response["success"] = 0;
                    //$response["message"] = "No user found";
                    echo json_encode($response);
                }
               
           } else {
               // no product found
               $response["success"] = 0;
               //$response["message"] = "No user found";
    
               // echo no users JSON
               echo json_encode($response);
           }
       } else {
           // no product found
           $response["success"] = 0;
           //$response["message"] = "No user found";
    
           // echo no users JSON
           echo json_encode($response);
       }
   } else {
       // required field is missing
       $response["success"] = 0;
       //$response["message"] = "Required field(s) is missing";
    
       // echoing JSON response
       echo json_encode($response);
   }
}

function KnnEkle($sicil, $tarih, $yer, $makno, $aciklama){
    
   // check for required fields
   if (!empty($sicil) && !empty($tarih) && !empty($yer) && !empty($makno) && !empty($aciklama)) {
    
       // mysql inserting a new row
       $result = mysql_query("INSERT INTO knn ( sicil, tarih, yer, makno, aciklama ) VALUES('$sicil', '$tarih', '$yer', '$makno', '$aciklama')");
    
       // check if row inserted or not
       if ($result) {
           // successfully inserted into database
           $response["success"] = 1;
           $response["message"] = "Veri kaydedildi.";
    
           // echoing JSON response
           echo json_encode($response);
       } else {
           // failed to insert row
           $response["success"] = 0;
           $response["message"] = "Veri kaydedilirlen bir hata ile karşılaşıldı.";
    
           // echoing JSON response
           echo json_encode($response);
       }
   } else {
       // required field is missing
       $response["success"] = 0;
       $response["message"] = "Eksik alan hatası";
    
       // echoing JSON response
       echo json_encode($response);
   }
}

function makineListesiGetir(){
    //$result = mysql_query("SELECT * FROM kontrolnoktalari") or die(mysql_error());
    $result = mysql_query("SELECT K.no, K.adi, Y.yalintakimadi, K.ust FROM (kontrolnoktalari AS K LEFT JOIN yalintakimlar AS Y ON K.no = Y.bolum) ORDER BY K.no ASC") or die(mysql_error());
    
   if (mysql_num_rows($result) > 0) {
       $response["makineler"] = array();
    
       while ($row = mysql_fetch_array($result)) {
            $makine = array();
            $makine["no"] = $row["no"];
            if($row["yalintakimadi"] != null){
                $makine["adi"] = $row["adi"]. " - ".$row["yalintakimadi"];
            }else{
                $makine["adi"] = $row["adi"];
            }           
            $makine["ust"] = $row["ust"];
           /*$makine["bolumrengi"] = $row["bolumrengi"];
           $makine["hatagenelleme"] = $row["hatagenelleme"];
           $makine["sapno"] = $row["sapno"];*/
    
           array_push($response["makineler"], $makine);
       }
       $response["success"] = 1;
    
       // echoing JSON response
       echo json_encode($response);
   } else {
       // no products found
       $response["success"] = 0;
       $response["message"] = "Makine Bulunamadı";
    
       // echo no users JSON
       echo json_encode($response);
   }
}

function topXHata($topX){
    $response["success"] = 0;
    //strtotime(date('m-01-Y'));
    $time = time()-2629743;
    //$time = strtotime(date('m-01-Y'));

    $resultHata = mysql_query("SELECT FC.hataadi AS HataText, Sum(FK.adet) AS ToplamHata FROM (fkhatakayit AS FK INNER JOIN fkhatacesitleri AS FC ON FK.hatano = FC.no) WHERE tarih > $time GROUP BY HataText ORDER BY ToplamHata DESC LIMIT 0,$topX") or die(mysql_error());

    $resultOperator = mysql_query("SELECT kisiler.adisoyadi AS HataText, Sum(fkhatakayit.adet) AS ToplamHata FROM (fkhatakayit INNER JOIN kisiler ON fkhatakayit.sicil = kisiler.sicil) WHERE tarih > $time GROUP BY AdiSoyadi ORDER BY ToplamHata DESC LIMIT 0,$topX") or die(mysql_error());

    $resultBolum = mysql_query("SELECT K.ust AS Ust, K.adi as HataText, SUM(F.adet) AS ToplamHata FROM (fkhatakayit AS F INNER JOIN kontrolnoktalari AS K on F.bolum = K.no )WHERE F.tarih > $time GROUP BY F.bolum ORDER BY ToplamHata DESC LIMIT 0,$topX");
   

    if (mysql_num_rows($resultHata) > 0) {
       $response["hatalarMost"] = array();
    
       while ($row = mysql_fetch_array($resultHata)) {
           $fkhata = array();
           
            
           $fkhata["HataText"] = $row["HataText"];
           $fkhata["ToplamHata"] = $row["ToplamHata"];

           array_push($response["hatalarMost"], $fkhata);
       }
       $response["success"] += 1;
    } else {
       // no products found
       $response["messageHatalarMost"] = "Hata Listesi Alınamadı";
    }
   
    if (mysql_num_rows($resultOperator) > 0) {
        $response["hatalarOperator"] = array();
 
        while ($row = mysql_fetch_array($resultOperator)) {
            $fkhata = array();
        
         
            $fkhata["HataText"] = $row["HataText"];
            $fkhata["ToplamHata"] = $row["ToplamHata"];

            array_push($response["hatalarOperator"], $fkhata);
        }
        $response["success"] += 1;
    } else {
        // no products found
        $response["messageHatalarOperator"] = "Hata Listesi alınamadı Bulunamadı";
    }

    if (mysql_num_rows($resultBolum) > 0) {
        $response["hatalarBolum"] = array();
 
        while ($row = mysql_fetch_array($resultBolum)) {
            $fkhata = array();
        
            if($row["Ust"] == "1"){
                $fkhata["HataText"] = "356 Sedan / " . $row["HataText"];
            }else if($row["Ust"] == "2"){
                $fkhata["HataText"] = "356 HB / " . $row["HataText"];
            }else if($row["Ust"] == "3"){
                $fkhata["HataText"] = "356 SW / " . $row["HataText"];
            }else{
                $fkhata["HataText"] = $row["HataText"];
            }
            $fkhata["ToplamHata"] = $row["ToplamHata"];

            array_push($response["hatalarBolum"], $fkhata);
        }
        $response["success"] += 1;
    } else {
        $response["messageHatalarBolum"] = "Hata Listesi Alınamadı Bulunamadı";
    }
    echo json_encode($response); 
}

function topCount(){
    $result = mysql_query("SELECT ayar, cikti FROM hata_ayar WHERE ayar = 'top_liste_adet'") or die(mysql_error());

    if (mysql_num_rows($result) > 0) {
        while ($row = mysql_fetch_array($result)) {
            $response["topCount"] = $row["cikti"];
        }
        echo json_encode($response);
    } else {
        // no products found
        $response["success"] = 0;
        $response["message"] = "Miktar Alınamadı";
     
        // echo no users JSON
        echo json_encode($response);
    }
}
?>