package com.wizbiz.wizard_card_game;

/**
 * PlayerCustomization - Stores player's visual appearance choices
 * Includes face style and wizard outfit components
 */
public class PlayerCustomization {

    // Face options now match your REAL image filenames (no extension)
    private String faceType;  // "RuggedWarrior", "WiseElder", "YoungProdigy"

    // Outfit components
    private String hatType;
    private String robeColor;
    private String staffType;

    // Player name
    private String playerName;

    // Default constructor
    public PlayerCustomization() {
        this.faceType = "WiseElder";   // default your first image
        this.hatType = "pointy_hat";
        this.robeColor = "blue";
        this.staffType = "wooden_staff";
        this.playerName = "Wizard";
    }

    // Constructor with custom values
    public PlayerCustomization(String faceType, String hatType, String robeColor,
                               String staffType, String playerName) {
        this.faceType = faceType;
        this.hatType = hatType;
        this.robeColor = robeColor;
        this.staffType = staffType;
        this.playerName = playerName;
    }

    // Getters
    public String getFaceType() { return faceType; }
    public String getHatType() { return hatType; }
    public String getRobeColor() { return robeColor; }
    public String getStaffType() { return staffType; }
    public String getPlayerName() { return playerName; }

    // Setters
    public void setFaceType(String faceType) { this.faceType = faceType; }
    public void setHatType(String hatType) { this.hatType = hatType; }
    public void setRobeColor(String robeColor) { this.robeColor = robeColor; }
    public void setStaffType(String staffType) { this.staffType = staffType; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    /**
     * FACE IMAGE — updated to load YOUR PHOTO FILES
     * RuggedWarrior.jpeg
     * WiseElder.jpeg
     * YoungProdigy.jpeg
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

    /**
     * Get the file path for the hat image
     */
    public String getHatImagePath() {
        if (hatType == null || hatType.isEmpty()) return "/images/hats/TopHat.jpg";
        // If caller already provided a filename (with extension), use it directly
        if (hatType.contains(".")) {
            return "/images/hats/" + hatType;
        }

        // Map internal tokens to the exact resource filenames you provided (jpg)
        return switch (hatType) {
            case "pointy_hat" -> "/images/hats/PointyHat.jpg";
            case "wide_brim_hat" -> "/images/hats/WideBrim.jpg";
            case "hood" -> "/images/hats/Hood.jpg";
            case "top_hat" -> "/images/hats/TopHat.jpg";
            case "crown" -> "/images/hats/TopHat.jpg"; // fallback to TopHat for crown
            default -> "/images/hats/TopHat.jpg";
        };
    }

    /**
     * Get the file path for the robe image
     */
    public String getRobeImagePath() {
        return "/images/robes/" + robeColor + "_robe.png";
    }

    /**
     * Get the file path for the staff image
     */
    public String getStaffImagePath() {
        return "/images/staffs/" + staffType + ".png";
    }

    /**
     * Composite avatar (you are not using this yet)
     */
    public String getCompositeAvatarPath() {
        return "/images/avatars/" + faceType + "_" + hatType + "_" +
                robeColor + "_" + staffType + ".png";
    }
}
