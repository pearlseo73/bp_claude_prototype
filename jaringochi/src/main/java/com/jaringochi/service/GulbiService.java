package com.jaringochi.service;

import com.jaringochi.domain.Gulbi;
import com.jaringochi.domain.GulbiChild;
import com.jaringochi.domain.GulbiClothes;
import com.jaringochi.repository.GulbiRepository;
import java.util.List;

public class GulbiService {
    private GulbiRepository gulbiRepository = new GulbiRepository();

    public Gulbi getGulbi(long userId) {
        return gulbiRepository.findByUserId(userId);
    }

    public void selectGulbi(long userId, String gulbiType) {
        Gulbi g = new Gulbi();
        g.setUserId(userId);
        g.setGulbiType(gulbiType);
        g.setWeight(5); // Default weight
        g.setStreakRecord(0);
        g.setStreakBudget(0);
        gulbiRepository.save(g);
    }

    public void setPersonality(long userId, String personality) {
        gulbiRepository.updatePersonality(userId, personality);
    }
    
    public boolean hasGulbi(long userId) {
        return getGulbi(userId) != null;
    }

    public List<GulbiClothes> getClothes(long userId) {
        return gulbiRepository.findClothesByUserId(userId);
    }

    public List<GulbiChild> getChildren(long userId) {
        return gulbiRepository.findChildrenByUserId(userId);
    }

    public void equipClothes(long userId, Long clothesId) {
        gulbiRepository.updateActiveClothes(userId, clothesId);
    }
}
