/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Vote;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import utils.ConnexionBD;
import iService.IVote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class GestionVote implements IVote{
     Connection c = ConnexionBD.getInstanceConnexionBD().getConnection();
    Statement ste;
    
    public GestionVote() {
        try {
            ste = c.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
   
     
    @Override
    public void ajouterVote(Vote A) {
      String req="INSERT INTO `vote` "
                    + "(`idjobeur_fg`,`idposteur_fg`,`nomj_fg`, `prenomj_fg`,`nb_like`) "
                    + "VALUES (?,?,?,?,?)";
            try{ PreparedStatement ste = c.prepareStatement(req);
            ste.setInt(1,A.getIdjobeur_fg());
            ste.setInt(2,A.getIdposteur_fg());
            ste.setString(3,A.getNomp());
            ste.setString(4,A.getPrenomp());
            ste.setInt(5,A.getNb_like()+1);
            ste.executeUpdate();
            JOptionPane.showMessageDialog(null," jobuer ajouté à votre liste favoris "); 
        } catch (SQLException e) {System.err.println(e);
    JOptionPane.showMessageDialog(null,"error ");
        }
    }
     @Override
    public void UpdateVote(Vote A, int cin_jobeur) {
          String req = "UPDATE  vote SET nb_like=1 ,nb_dislike=0 WHERE idjobeur_fg="+cin_jobeur;
 
     try { PreparedStatement ste = c.prepareStatement(req);
            ste.executeUpdate();
            System.out.println(" successfully modified!");
            } catch (SQLException e) {System.err.println(e);
    JOptionPane.showMessageDialog(null,"error ");
    }}
    @Override
     public void UpdateVotedislike(Vote A, int cin_jobeur) {
          String req = "UPDATE  vote SET nb_like=0 ,nb_dislike=1 WHERE idjobeur_fg="+cin_jobeur;
     try { PreparedStatement ste = c.prepareStatement(req);
            ste.executeUpdate();
            System.out.println(" successfully modified!");
            } catch (SQLException e) {System.err.println(e);
    JOptionPane.showMessageDialog(null,"error ");
    }}
     @Override
    public void ajouterVotedislike(Vote A) {
       String req="INSERT INTO `vote` "
                    + "(`idjobeur_fg`,`idposteur_fg`,`nomj_fg`, `prenomj_fg`,`nb_dislike`) "
                    + "VALUES (?,?,?,?,?)";
       System.out.println("zzzzzzzzz"+req); 
            try{ PreparedStatement ste = c.prepareStatement(req);
            ste.setInt(1,A.getIdjobeur_fg());
            ste.setInt(2,A.getIdposteur_fg());
            ste.setString(3,A.getNomp());
            ste.setString(4,A.getPrenomp());
            ste.setInt(5,A.getNb_dislike()+1);
            ste.executeUpdate();
            JOptionPane.showMessageDialog(null," jobuer ajouté à votre liste favoris "); 
        } catch (SQLException e) {System.err.println(e);
    JOptionPane.showMessageDialog(null,"error ");
        }
    }
    
    @Override
    public void supprimerVote(Vote A) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vote> afficherVote() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vote> afficherVote(int cin_jobeur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
  public boolean verificationvote (int cin_p,int cin_j,String choix) throws SQLException{
         String req="select * from vote where idposteur_fg="+cin_p+" and idjobeur_fg="+cin_j+" and "+choix+"=1";
         System.out.println("zzzzzzzzz"+req); 
         ResultSet res=  ste.executeQuery(req);
          if (res.next()) {
           return true;
          }  return false;
     }
   public boolean verificationvote1 (int cin_p,int cin_j) throws SQLException{
         String req="select * from vote where idposteur_fg="+cin_p+" and idjobeur_fg="+cin_j;
         System.out.println("zzzzzzzzz"+req); 
         ResultSet res=  ste.executeQuery(req);
          if (res.next()) {
           return true;
          }  return false;}

}
