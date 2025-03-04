
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    
    public Connection connectDB(){
        Connection conn = null;
        
        try {
        
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/leiloestdsat?useSSL=false&allowPublicKeyRetrieval=true", // linha de conexao
                    "root", // usuario do mysql
                    "Godisgay2801"// senha do mysql
            );
            JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso!");
            
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO" + erro.getMessage());
        }
        return conn;
    }
    //Método para encerrar a conexão
        public void fecharConexao(Connection conn){
            try {
                if(conn != null){
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Conexão encerrada com sucesso!");               
                }
            }catch (SQLException erro){
                JOptionPane.showMessageDialog(null, "Erro a encerrar a conexão" + erro.getMessage());
            }
        }   
}
