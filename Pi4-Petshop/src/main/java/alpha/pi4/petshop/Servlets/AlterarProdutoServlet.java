/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alpha.pi4.petshop.Servlets;

import alpha.pi4.petshop.BLL.ProdutoBLL;
import alpha.pi4.petshop.modelos.Produto;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giovanebarreira
 */
@WebServlet(name = "AlterarProdutoServlet", urlPatterns = {"/AlterarProdutoServlet"})
public class AlterarProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       int id = Integer.parseInt(request.getParameter("id"));
       
       Produto p = null;
       List<Produto> produtos = null;
       
       try{
           p = ProdutoBLL.obterProduto(id);
           produtos = ProdutoBLL.listar("");
       }
        catch(Exception ex){
            ex.printStackTrace();
        }
       
       //Com os dados do cliente em mãos, podemos enviá-lo como atributo para a página cliente.jsp para que seja feita a alteração do seu cadastro
        request.setAttribute("produto", p);
        request.setAttribute("produtos", produtos);
        
        //Caso não ocorra nenhum erro, a página cliente.jsp deve ser exibida com o formulário preenchido com os dados atuais do cliente
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/produtos.jsp");
        dispatcher.forward(request, response);
    }



    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            int id = Integer.parseInt(request.getParameter("id"));
        Produto p = null;
        List<Produto> produtos = null;
        
        try{
            p = ProdutoBLL.obterProduto(id); //Com isso, obtemos novamente o cliente para que seja realizado de fato a alteração de seu cadastro
            
            //É feito o armazenamento dos dados que foram preenchidos no formulário, da mesma forma que foi feito no servlet ClienteServlet
                p.setNome(request.getParameter("nome"));
                p.setPreco(BigDecimal.valueOf(Double.parseDouble(request.getParameter("preco"))));
                p.setFabricante(request.getParameter("fabricante"));
                p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                p.setModelo(request.getParameter("modelo"));
                p.setCodBarras(request.getParameter("codbarras"));
              //  p.setFilial(FilialDAO.obterFilial(Integer.parseInt(request.getParameter("filial"))));

            
            //Depois de armazenar os novos dados do cliente, é chamado o método de validação alterar().
            //Esse método, por sua vez, chama o método responsável por realizar a alteração no banco de dados (caso todos os dados estejam válidos)
            ProdutoBLL.alterar(p);
            
            produtos = ProdutoBLL.listar("");
            request.setAttribute("produtos", produtos);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/ProdutoServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    } 
}