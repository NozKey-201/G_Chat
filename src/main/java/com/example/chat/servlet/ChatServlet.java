package com.example.chat.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.chat.model.Message;
import com.example.chat.service.GeminiService;

/**
 * チャット機能を処理するサーブレット
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    private GeminiService geminiService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        geminiService = new GeminiService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // セッションから会話履歴を取得
        HttpSession session = request.getSession();
        List<Message> messages = (List<Message>) session.getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList<>();
        }
        
        // ユーザーメッセージの取得
        String userMessage = request.getParameter("message");
        
        if (userMessage != null && !userMessage.trim().isEmpty()) {
            // ユーザーメッセージを履歴に追加
            messages.add(new Message(userMessage, "user"));
            
            // Gemini APIに送信して応答を取得
            String geminiResponse = geminiService.sendMessage(userMessage);
            
            // Geminiの応答を履歴に追加
            messages.add(new Message(geminiResponse, "assistant"));
            
            // セッションに保存
            session.setAttribute("messages", messages);
        }
        
        // index.jspにリダイレクト
        response.sendRedirect("index.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // GETリクエストの場合もindex.jspにリダイレクト
        response.sendRedirect("index.jsp");
    }
}