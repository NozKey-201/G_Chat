package com.example.chat.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 会話履歴をクリアするサーブレット
 */
@WebServlet("/clear")
public class ClearServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // セッションから会話履歴を削除
        HttpSession session = request.getSession();
        session.removeAttribute("messages");
        
        // index.jspにリダイレクト
        response.sendRedirect("index.jsp");
    }
}