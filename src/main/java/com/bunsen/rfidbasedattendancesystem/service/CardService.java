package com.bunsen.rfidbasedattendancesystem.service;

import com.bunsen.rfidbasedattendancesystem.model.Card;
import com.bunsen.rfidbasedattendancesystem.repository.CardRepository;
import org.springframework.stereotype.Service;
@Service
public class CardService {
    private final CardRepository cardRepo;

    public CardService(CardRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    public Card findByCardNumber(String cardNo) {
        return cardRepo.findByCardNumber(cardNo);
    }

    public void save(Card newCard) {
        cardRepo.save(newCard);
    }
}