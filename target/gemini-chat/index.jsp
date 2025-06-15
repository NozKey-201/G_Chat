<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.chat.model.Message" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gemini Chat 7</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>AIチャット</h1>
            <p>Gemini版</p>
        </header>
        
        <div class="chat-container">
            <div class="messages" id="messages">
                <%
                    // セッションから会話履歴を取得
                    List<Message> messages = (List<Message>) session.getAttribute("messages");
                    if (messages != null) {
                        for (Message msg : messages) {
                %>
                    <div class="message <%= msg.getSender() %>-message">
                        <div class="message-header">
                            <span class="sender"><%= msg.getSender().equals("user") ? "あなた" : "Gemini" %></span>
                            <span class="timestamp"><%= msg.getFormattedTimestamp() %></span>
                        </div>
                        <div class="message-content">
                            <%= msg.getContent().replace("\n", "<br>") %>
                        </div>
                    </div>
                <%
                        }
                    }
                %>
            </div>
            
            <form action="chat" method="post" class="input-form">
                <div class="input-group">
                    <input type="text" name="message" placeholder="メッセージを入力..." 
                           autocomplete="off" required autofocus>
                    <button type="submit">送信</button>
                </div>
            </form>
        </div>
        
        <div class="actions">
            <form action="clear" method="post" style="display: inline;">
                <button type="submit" class="clear-btn">会話をクリア</button>
            </form>
        </div>
    </div>
    
    <script>
        // ページ読み込み時に最下部にスクロール
        window.onload = function() {
            var messages = document.getElementById('messages');
            messages.scrollTop = messages.scrollHeight;
        };
    </script>
</body>
</html>