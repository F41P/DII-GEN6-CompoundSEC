package utils;

import models.Card;

public abstract class CardDecorator extends Card {
    protected Card card;

    // Constructor ของ CardDecorator ที่รับ 4 พารามิเตอร์
    public CardDecorator(Card card) {
        super(card.getCardID(), card.getRoom(), card.getFloor(), card.isVIP()); // เรียก constructor ของ Card
        this.card = card;
    }

    public abstract boolean canAccess(String floor);
}
