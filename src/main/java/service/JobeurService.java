/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entites.Jobeur;
import entites.Posteur;
import iService.IJobeur;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import static service.PosteurService.A1;
import utils.ConnexionBD;


/**
 *
 * @author lenovo
 */
public class JobeurService implements IJobeur{
    public static Image A1;

    Connection c = ConnexionBD
           .getInstanceConnexionBD()
           .getConnection();
    Statement ste;

    public JobeurService() {
        try {
            ste = c.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void creerJobeur(Jobeur p, FileInputStream fis, File file) {
         String req1 = "insert into jobeur (cin, nom, prenom, email, sexe, password, date_naissance, tel, role, job, address, etat, image_j) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ste = c.prepareStatement(req1);
            ste.setInt(1, p.getCin());
            ste.setString(2, p.getNom());
            ste.setString(3, p.getPrenom());
            ste.setString(4, p.getEmail());
            ste.setString(5, p.getSexe());
            ste.setString(6, p.getPassword());
            ste.setDate(7, (Date) p.getDate_naissance());
            ste.setInt(8, p.getTel());
            ste.setString(9, "Jobeur");
            ste.setString(10, p.getJob());
            ste.setString(11, p.getAddress());
            ste.setString(12, "non_valide");
            ste.setBinaryStream(13, (InputStream)fis, (int)file.length());
            
            ste.executeUpdate();
            System.out.println("Ajout Complete");
            JOptionPane.showMessageDialog(null, "Account Created Successfull");

        } catch (SQLException ex) {
            Logger.getLogger(PosteurService.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Cin is already used by another ones");
        }
    }
    public void creerJobeur(Jobeur p) {
         String req1 = "insert into jobeur (cin, nom, prenom, email, sexe, password, date_naissance, tel, role, job, address, etat) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ste = c.prepareStatement(req1);
            ste.setInt(1, p.getCin());
            ste.setString(2, p.getNom());
            ste.setString(3, p.getPrenom());
            ste.setString(4, p.getEmail());
            ste.setString(5, p.getSexe());
            ste.setString(6, p.getPassword());
            ste.setDate(7, (Date) p.getDate_naissance());
            ste.setInt(8, p.getTel());
            ste.setString(9, "Jobeur");
            ste.setString(10, p.getJob());
            ste.setString(11, p.getAddress());
            ste.setString(12, "non_valide");
            
            
            ste.executeUpdate();
            System.out.println("Ajout Complete");
            JOptionPane.showMessageDialog(null, "Account Created Successfull");

        } catch (SQLException ex) {
            Logger.getLogger(PosteurService.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Cin is already used by another ones");
        }
    }
    
    public Jobeur getJobeurInfobyCin(int cin1) throws FileNotFoundException, SQLException, IOException
    {
        Jobeur p = new Jobeur();
      String req2="select * from jobeur where cin="+cin1;   
      try {
          ResultSet res=  ste.executeQuery(req2);
          while (res.next()) { 
                      p.setId( res.getInt("id"));
                      p.setCin(res.getInt("cin") );
                      p.setNom(res.getString("nom"));
                      p.setPrenom(res.getString("prenom"));
                      p.setEmail(res.getString("email"));
                      p.setSexe(res.getString("sexe"));
                      p.setPassword(res.getString("password"));
                      p.setDate_naissance(res.getDate("date_naissance"));
                      p.setTel(res.getInt("tel"));
                      p.setRole(res.getString("role"));
                      p.setEtat(res.getString("etat")); 
                      p.setJob(res.getString("job"));
                      p.setAddress(res.getString("address"));
                      
                      System.out.println(res.getBytes("image_p"));
                      if(res.getBytes("image_p") != null)
                      {
                          InputStream is = res.getBinaryStream("image_p");
                      OutputStream os = new FileOutputStream( new File("img.jpg"));
                      byte[] content = new byte[2048];
                      int size = 0;
                     while((size = is.read(content)) != -1){
                          os.write(content, 0, size);
                      }
                     Image image1=new Image("file:img.jpg");
               A1=image1;
               System.out.println(A1);
                      
                        }
                      }  
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      } 
        return p;   }

    @Override
    public void modifierJobeur(Jobeur p) {
        try {
            String update = "UPDATE jobeur SET  cin = ? , nom = ? , prenom = ? , email = ? ,sexe = ? ,"
                    + "password = ? , date_naissance = ? , tel = ?,job=? ,address=? WHERE cin = ? ";
            PreparedStatement st2 = c.prepareStatement(update);
            st2.setInt(1, p.getCin());
            st2.setString(2, p.getNom());
            st2.setString(3, p.getPrenom());
            st2.setString(4, p.getEmail());
            st2.setString(5, p.getSexe());
            st2.setString(6, p.getPassword());
            st2.setDate(7, p.getDate_naissance());
            st2.setInt(8, p.getTel());
            st2.setInt(11, p.getCin());
            st2.setString(9, p.getJob());
            st2.setString(10, p.getAddress());
            

            st2.executeUpdate();
            System.out.println("" + p.getCin() + " successfully modified!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.err.println("" + p.getCin() + " error modification!!");
        }
    }

    @Override
    public void supprimerJobeur(Jobeur p) {
        try {
            String req1="delete from jobeur where"
                    + " cin=?";
       
      PreparedStatement ps = c.prepareStatement(req1);
            ps.setInt(1, p.getCin());
            ps.executeUpdate();
            System.out.println("Delete Complete");

        } catch (SQLException ex) {
            Logger.getLogger(PosteurService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Jobeur> afficherJobeur() {
        List<Jobeur> jobeurs = new ArrayList<>();
      Jobeur p = null ;
      String req2="select * from jobeur";
      try {
         
         
          ResultSet res=  ste.executeQuery(req2);
          while (res.next()) { 
              p = new Jobeur();
                      p.setId( res.getInt("id"));
                      p.setCin(res.getInt("cin") );
                      p.setNom(res.getString("nom"));
                      p.setPrenom(res.getString("prenom"));
                      p.setEmail(res.getString("email"));
                      p.setSexe(res.getString("sexe"));
                      p.setPassword(res.getString("password"));
                      p.setDate_naissance(res.getDate("date_naissance"));
                      p.setTel(res.getInt("tel"));
                      p.setRole(res.getString("role"));
                      p.setEtat(res.getString("etat"));
                      p.setAddress(res.getString("address"));
                      p.setJob(res.getString("job"));

 jobeurs.add(p);
  }
          
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      } 
        
     return jobeurs;
    }
    public List<Jobeur> afficherJobeurbyEtat(String nom) throws SQLException {
        List<Jobeur> jobeurs = new ArrayList<>();
      Jobeur p=null;
      String req1="select * from Posteur where etat LIKE '"+nom+"'";;
      System.out.println(req1);
      ResultSet res=  ste.executeQuery(req1);
          //ResultSet res=  ste.executeQuery(req2);
          while (res.next()) { 
              p = new Jobeur();
                      p.setId( res.getInt("id"));
                      p.setCin(res.getInt("cin") );
                      p.setNom(res.getString("nom"));
                      p.setPrenom(res.getString("prenom"));
                      p.setEmail(res.getString("email"));
                      p.setSexe(res.getString("sexe"));
                      p.setPassword(res.getString("password"));
                      p.setDate_naissance(res.getDate("date_naissance"));
                      p.setTel(res.getInt("tel"));
                      p.setRole(res.getString("role"));
                      p.setEtat(res.getString("etat"));
                      p.setAddress(res.getString("address"));
                      p.setJob(res.getString("job"));
 jobeurs.add(p);
          }
     return jobeurs;
    }
      
        public void BannirJobeur(Jobeur p) {
try {
            String update = "UPDATE jobeur SET  cin = ?, etat = ?, role= ? WHERE cin = ? ";
            PreparedStatement st2 = c.prepareStatement(update);
            st2.setInt(1, p.getCin());
            st2.setString(2, "banned");
            st2.setString(3, p.getRole());
            st2.setInt(4, p.getCin());
            st2.executeUpdate();
            System.out.println("" + p.getCin() + " successfully Bannned!");
            JOptionPane.showMessageDialog(null, "Acc successfully Bannned");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.err.println("" + p.getCin() + " error bannir!!");
        }
    }

    @Override
    public void modifierProfil(Jobeur p, InputStream fis, File file) {
    }
    
}