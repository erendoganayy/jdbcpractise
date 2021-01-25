import java.sql.*;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.System.console;
import static java.lang.System.exit;

public class Baglanti {
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    private String kullaniciAdi="root";
    private String parola="";
    private String dbIsmi="demo";
    private String host="localhost";
    private int port=3306;
    private Connection con=null;
    public void preparedCalisanlariGetir(int id){
        String sorgu="SELECT * from calisanlar where id = ?";
       try{ preparedStatement=con.prepareStatement(sorgu);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String ad = resultSet.getString("ad");
            String soyad = resultSet.getString("soyad");
            String email=resultSet.getString("email");
            System.out.println("Ad: "+ad+" Soyad : "+soyad+" Email : "+email);
        }
       }
       catch (SQLException ex){
           ex.printStackTrace();
       }

    }
    public void calisanSil(int idd){

        try{
        String sorgu = "delete from calisanlar where id=?";
        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
        preparedStatement.setInt(1,idd);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void calisanGuncelle(int hangiID,String isim,String soyisim,String mail){
        try{
            String sorgu="update calisanlar set ad = ? , soyad=? , email=? where ID=? ";
            PreparedStatement preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,isim);
            preparedStatement.setString(2,soyisim);
            preparedStatement.setString(3,mail);
            preparedStatement.setInt(4,hangiID);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch (SQLException e){e.printStackTrace();}


    }
    public void calisanEkle(String ad,String soyad,String email){
        try{
        statement=con.createStatement();
        /*String ad="Semih";
        String soyad="Aktas";
        String email="smhakts@gmail.com";*/

        String sorgu="insert into calisanlar (ad,soyad,email) VALUES(" + "'" + ad + "',"+"'" + soyad + "',"+"'" + email + "')";
        statement.executeUpdate(sorgu);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void calisanGetir(){
        String sorgu= "Select * from calisanlar";
        try  {
           statement=con.createStatement();
          ResultSet resultSet = statement.executeQuery(sorgu);
          while (resultSet.next()){
              int id = resultSet.getInt("ID");
              String ad = resultSet.getString("ad");
              String soyad=resultSet.getString("soyad");
              String email= resultSet.getString("email");

              System.out.println("ID: "+id+" Ad "+ad+"Soyad : "+soyad+"Email : "+email);
          }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    public Baglanti(){
        //"jdbc:mysql://localhost:3306/demo"
String url="jdbc:mysql://"+host+":"+port+"/"+dbIsmi+"?useUnicode=true&characterEncoding=utf8";
try{
   Class.forName("com.mysql.jdbc.Driver");
}
catch (ClassNotFoundException e){
    System.out.println("Driver Bulunamadi");
}
    try {
        con = DriverManager.getConnection(url, kullaniciAdi, parola);
        System.out.println("basarili baglanti.");
    }
    catch  (SQLException ex) {
        System.out.println("Basarisiz baglanti!");
    }

    }

    public static void main(String[] args) {
        Baglanti baglanti=new Baglanti();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Selamlar veritabanina hosgeldiniz!");
        System.out.println("*************************************************");

        while(true) {
            System.out.println("İslem seciniz:\n" +
                    "1-Calisan Ekle\n" +
                    "2-Calisan Bilgilerini guncelle\n" +
                    "3-Calisani kov\n" +
                    "4-Calisan Listesini goruntule\n" +
                    "q-CIKIS");
            String islem=scanner.nextLine();
            if (islem.equals("q")) {
                System.out.println("Cikiliyor.");
               // exit();
                break;
                    }
            else if(islem.equals("1")){

                System.out.println("CALISAN ISMI: ");
                String isim=scanner.nextLine();
                System.out.println("CALISAN SOYAD: ");
                String soyisim=scanner.nextLine();
                System.out.println("CALISAN MAIL: ");
                String mail=scanner.nextLine();
                baglanti.calisanEkle(isim,soyisim,mail);
                baglanti.calisanGetir();

            }
            else if(islem.equals("2")){
                System.out.println("GUNCELLEMEK ISTEDIGINIZ CALISANIN ID: ");
                int hangiid=scanner.nextInt();
                scanner.nextLine();
                System.out.println("YENİ CALISAN ISMI: ");
                String isim=scanner.nextLine();
                System.out.println("YENİ CALISAN SOYAD: ");
                String soyisim=scanner.nextLine();
                System.out.println("YENİ CALISAN MAIL: ");
                String mail=scanner.nextLine();
                baglanti.calisanGuncelle(hangiid,isim,soyisim,mail);
                baglanti.calisanGetir();

            }
            else if (islem.equals("3")){
                System.out.println("KOVMAK İSTEDİGİNİZ CALİSANİN ID SİNİ GİRİNİZ ");
                baglanti.calisanGetir();
                System.out.println("ID GIR : ");
                int idd=scanner.nextInt();
                scanner.nextLine();
                baglanti.calisanSil(idd);
                System.out.println(idd+"ID sine sahip calisan veritabanindan silinmistir. Güncellenmis calisan listesi : ");
                baglanti.calisanGetir();

            }
            else if (islem.equals("4")){
                baglanti.calisanGetir();
            }
            else {
                System.out.println("GECERSIZ BIR ISLEM GIRDINIZ ! ");
            }
        }
        /*
        System.out.println("silinmeden once .....................");
        baglanti.calisanGetir();
        System.out.println("**********************************************");
        System.out.println("silindikten sonra *************");
        baglanti.calisanSil();
        baglanti.calisanGetir();*/



    }


}
