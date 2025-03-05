/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    // Método para cadastrar um produto no banco de dados
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produto (nome, valor, status) VALUES (?, ?, ?)";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (HeadlessException | SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
        }
    }
    
    // Método para listar os produtos cadastrados no banco de dados
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produto";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            // Iterar sobre os resultados da consulta
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);  // Adicionar o produto à lista
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        } finally {
            // Fechar os recursos após a operação
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar recursos: " + e.getMessage());
            }
            new conectaDAO().fecharConexao(conn);
        }

        return listagem;  // Retornar a lista de produtos
    }
    
    // Método para listar apenas os produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM produto WHERE status = 'Vendido'";
        ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            // Iterar sobre os resultados da consulta
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listaVendidos.add(produto);  // Adicionar à lista de produtos vendidos
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + erro.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar recursos: " + e.getMessage());
            }
            new conectaDAO().fecharConexao(conn);
        }

        return listaVendidos;  // Retorna apenas os produtos vendidos
    }

    // Método para marcar um produto como vendido
    public void venderProduto(int idProduto) {
        String sql = "UPDATE produto SET status = ? WHERE id = ?";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, idProduto);

            int linhasAfetadas = prep.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }

        } catch (HeadlessException | SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + erro.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar recursos: " + e.getMessage());
            }
            new conectaDAO().fecharConexao(conn);
        }
    }     
}

