package controllers;

import models.Card;

import java.util.HashMap;
import java.util.Map;

public class CardManager {
    // การเก็บข้อมูลการ์ดใน Map (ใช้หลักการ Encapsulation เพื่อปกปิดข้อมูล)
    private static final Map<String, Card> cards = new HashMap<>();

    public static void addCard(Card card) {
        // การใช้หลักการ Polymorphism ในการทำงานของ addCard
        cards.put(card.getCardID(), card);
    }

    public static Card getCard(String cardID) {
        return cards.get(cardID);// คืนค่าข้อมูลการ์ดที่ตรงกับ cardID
    }
}
