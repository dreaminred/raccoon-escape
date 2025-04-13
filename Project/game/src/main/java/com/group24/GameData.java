package com.group24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {

    private Map<Class<?>, List<?>> entityMap;

    /**
     * Constructs a new {@code Game} instance with empty lists for players, enemies, rewards, bonus rewards, and springs.
     */
    public GameData() {
        entityMap = new HashMap<>();
        entityMap.put(Player.class, new ArrayList<Player>());
        entityMap.put(Enemy.class, new ArrayList<Enemy>());
        entityMap.put(Reward.class, new ArrayList<Reward>());
        entityMap.put(BonusReward.class, new ArrayList<BonusReward>());
        entityMap.put(Spring.class, new ArrayList<Spring>());
    }

    /**
     * Constructs a new {@code Game} instance with specified lists for players, enemies, rewards, 
     * bonus rewards, and springs.
     *
     * @param players the list of players
     * @param enemies the list of enemies
     * @param rewards the list of rewards
     * @param bonusRewards the list of bonus rewards
     * @param springs the list of springs
     */
    public GameData(ArrayList<Player> players, ArrayList<Enemy> enemies, ArrayList<Reward> rewards, 
                ArrayList<BonusReward> bonusRewards, ArrayList<Spring> springs) {
        entityMap = new HashMap<>();
        entityMap.put(Player.class, players);
        entityMap.put(Enemy.class, enemies);
        entityMap.put(Reward.class, rewards);
        entityMap.put(BonusReward.class, bonusRewards);
        entityMap.put(Spring.class, springs);
    }

    /**
     * Adds an object to the appropriate list based on its type.
     *
     * @param obj the object to add
     * @throws IllegalArgumentException if the object type is not recognized
     */
    @SuppressWarnings("unchecked")
    public <T> void add(T obj) {
        List<T> list = (List<T>) entityMap.get(obj.getClass());
        if (list != null) {
            list.add(obj);
                    }
        else {
            throw new IllegalArgumentException("Unknown object type: " + obj.getClass().getSimpleName());  
        }
    }

    /**
     * Clears all lists in the game.
     */
    public void clear() {
        for (List<?> list : entityMap.values()) {
            list.clear();
        }
    }

    // Getters and Setters

    @SuppressWarnings("unchecked")
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) entityMap.get(Player.class);
    }

    @SuppressWarnings("unchecked")
    public void setPlayers(List<Player> players) {
        entityMap.put(Player.class, players);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Enemy> getEnemies() {
        return (ArrayList<Enemy>) entityMap.get(Enemy.class);
    }

    @SuppressWarnings("unchecked")
    public void setEnemies(List<Enemy> enemies) {
        entityMap.put(Enemy.class, enemies);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Reward> getRewards() {
        return (ArrayList<Reward>) entityMap.get(Reward.class);
    }

    @SuppressWarnings("unchecked")
    public void setRewards(List<Reward> rewards) {
        entityMap.put(Reward.class, rewards);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BonusReward> getBonusRewards() {
        return (ArrayList<BonusReward>) entityMap.get(BonusReward.class);
    }

    @SuppressWarnings("unchecked")
    public void setBonusRewards(List<BonusReward> bonusRewards) {
        entityMap.put(BonusReward.class, bonusRewards);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Spring> getSprings() {
        return (ArrayList<Spring>) entityMap.get(Spring.class);
    }

    @SuppressWarnings("unchecked")
    public void setSprings(List<Spring> springs) {
        entityMap.put(Spring.class, springs);
    }

}
