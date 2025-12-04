package com.wizbiz.wizard_card_game;

import java.util.Random;

/**
 * EnemyCustomization - Generates enemy appearance based on player's choices
 * Uses opposite colors and random face (different from player)
 */
public class EnemyCustomization {

    private String faceType;
    private String hatType;
    private String robeColor;
    private String staffType;
    private String enemyName;

    // Evil wizard names for random selection
    private static final String[] EVIL_NAMES = {
            "Malachar", "Vexor", "Shadowmane", "Dreadmoor", "Nightshade",
            "Morgath", "Grimveil", "Darkflame", "Ravenclaw", "Thornhex",
            "Blackthorn", "Venomspire", "Skullcrusher", "Doomweaver", "Bloodmoon"
    };

    /**
     * Generate enemy customization based on player's choices
     * Uses opposite colors and random face (different from player)
     */
    public EnemyCustomization(PlayerCustomization playerCustom) {
        Random rand = new Random();

        // Random face (different from player if possible)
        String[] faces = {"RuggedWarrior", "WiseElder", "YoungProdigy"};
        do {
            faceType = faces[rand.nextInt(faces.length)];
        } while (faceType.equals(playerCustom.getFaceType()) && faces.length > 1);

        // Opposite hat type
        hatType = getOppositeHat(playerCustom.getHatType());

        // Opposite robe color
        robeColor = getOppositeColor(playerCustom.getRobeColor());

        // Opposite staff type
        staffType = getOppositeStaff(playerCustom.getStaffType());

        // Random evil name
        enemyName = EVIL_NAMES[rand.nextInt(EVIL_NAMES.length)];
    }

    /**
     * Get opposite hat type
     */
    private String getOppositeHat(String playerHat) {
        switch (playerHat) {
            case "pointy_hat":
                return "wide_brim_hat";
            case "wide_brim_hat":
                return "pointy_hat";
            case "crown":
                return "hood";
            case "hood":
                return "crown";
            case "top_hat":
                return "hood";
            default:
                return "hood";
        }
    }

    /**
     * Get opposite robe color
     */
    private String getOppositeColor(String playerColor) {
        switch (playerColor) {
            case "blue":
                return "red";
            case "red":
                return "blue";
            case "purple":
                return "green";
            case "green":
                return "purple";
            case "black":
                return "white";
            case "white":
                return "black";
            default:
                return "black";
        }
    }

    /**
     * Get opposite staff type
     */
    private String getOppositeStaff(String playerStaff) {
        switch (playerStaff) {
            case "wooden_staff":
                return "bone_staff";
            case "bone_staff":
                return "wooden_staff";
            case "crystal_staff":
                return "gold_staff";
            case "gold_staff":
                return "crystal_staff";
            default:
                return "bone_staff";
        }
    }

    // Getters
    public String getFaceType() { return faceType; }
    public String getHatType() { return hatType; }
    public String getRobeColor() { return robeColor; }
    public String getStaffType() { return staffType; }
    public String getEnemyName() { return enemyName; }

    /**
     * Get image paths (same as PlayerCustomization)
     */
    public String getFaceImagePath() {
        return "/images/faces/" + faceType + getFaceExtension(faceType);
    }

    // Maps your filenames to their actual extensions
    private String getFaceExtension(String name) {
        return switch (name) {
            case "RuggedWarrior", "WiseElder", "YoungProdigy" -> ".jpeg";
            default -> ".png"; // fallback
        };
    }

    public String getHatImagePath() { if (hatType == null || hatType.isEmpty()) return "/images/hats/TopHat.jpg";
        if (hatType.contains(".")) return "/images/hats/" + hatType;
        return switch (hatType) {
            case "pointy_hat" -> "/images/hats/PointyHat.jpg";
            case "wide_brim_hat" -> "/images/hats/WideBrim.jpg";
            case "hood" -> "/images/hats/Hood.jpg";
            case "top_hat" -> "/images/hats/TopHat.jpg";
            case "crown" -> "/images/hats/TopHat.jpg";
            default -> "/images/hats/TopHat.jpg";
        };
    }

    public String getRobeImagePath() {
        return "/images/robes/" + robeColor + "_robe.png";
    }

    public String getStaffImagePath() {
        return "/images/staffs/" + staffType + ".png";
    }

    public String getCompositeAvatarPath() {
        return "/images/avatars/" + faceType + "_" + hatType + "_" +
                robeColor + "_" + staffType + ".png";
    }
}