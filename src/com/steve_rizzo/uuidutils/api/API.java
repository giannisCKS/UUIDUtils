package com.steve_rizzo.uuidutils.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.steve_rizzo.uuidutils.Utils;

public class API {

    Utils utils;

    public API(Utils utils) {

        this.utils = utils;

    }

    // Grabs the UUID from a player's name.
    public String getUUIDfromName(String playerName) {

        String uuid = "";

        try {

            String webData = utils.readUrl("https://api.mojang.com/users/profiles/minecraft/" + playerName);

            Gson gson = new Gson();

            JsonObject uuidData = gson.fromJson(webData, JsonObject.class);

            uuid = uuidData.get("id").getAsString();

        } catch (Exception e) {

            utils.sendErrorMessage("Please re-run the program and \n make sure the proper name is used!");

        }

        return uuid;

    }

}
