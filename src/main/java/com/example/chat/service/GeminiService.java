package com.example.chat.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Gemini APIと通信するクラス
 */
public class GeminiService {
    //テスト用
    //private static final String API_KEY = "";
    // APIキーはLinuxの環境変数から取得する

    private static final String API_KEY = System.getenv("GEMINI_API_KEY");
    



    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-8b:generateContent?key=" + API_KEY;
    
    /**
     * Gemini APIにメッセージを送信して応答を取得する
     * @param userMessage ユーザーからのメッセージ
     * @return Geminiからの応答
     */
    public String sendMessage(String userMessage) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // リクエストの設定
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // リクエストボディの作成
            JSONObject requestBody = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONArray parts = new JSONArray();
            JSONObject part = new JSONObject();
            
            part.put("text", userMessage);
            parts.put(part);
            content.put("parts", parts);
            contents.put(content);
            requestBody.put("contents", contents);
            
            // リクエスト送信
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // レスポンスの読み取り
            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8")
            );
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            
            // レスポンスの解析
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray candidates = jsonResponse.getJSONArray("candidates");
            if (candidates.length() > 0) {
                JSONObject firstCandidate = candidates.getJSONObject(0);
                JSONObject contentObj = firstCandidate.getJSONObject("content");
                JSONArray partsArray = contentObj.getJSONArray("parts");
                if (partsArray.length() > 0) {
                    return partsArray.getJSONObject(0).getString("text");
                }
            }
            
            return "応答を取得できませんでした。";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "エラーが発生しました: " + e.getMessage();
        }
    }
}