/*
 * COPYRIGHT:
 * Copyright (c) 2014  Steve Rizzo JR. (Fight_Or_Die)
 *
 * LICENSE:
 *   This is a Bukkit plugin designed to add new feature/s/ to the servers
 *   that this plugin is offered to.
 *
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     For more information regarding this Copyright, see http://www.gnu.org/licenses/gpl.txt.
 *
 * ----
 * OTHER NOTES:
 * - Profits from redistribution of any means are NOT permitted and may result in further action.
 * - Modified versions of this work may NOT be posted on the same locations (sites) in which the original (this) is posted.
 *
 * CONTACT:
 * TWITTER - twitter.com/djstrizz
 * WEBSITE - https://www.steve-rizzo.com
 * Fight_Or_Die (Bukkit Plugin Development)
 * ----
 */

package com.steve_rizzo.uuidutils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;

import java.util.Iterator;
import java.util.Scanner;

public class Utils {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to see [CURRENT] or [PAST] info?");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("current") || (input.equalsIgnoreCase("past"))) {

            switch (input.toUpperCase()) {

                case "CURRENT":

                    System.out.println("Enter a user's name: ");
                    input = scanner.nextLine();

                    try {

                        String webData = readUrl("https://api.mojang.com/users/profiles/minecraft/" + input);

                        Gson gson = new Gson();

                        JsonObject uuidData = gson.fromJson(webData, JsonObject.class);

                        String uuid = uuidData.get("id").getAsString();
                        String name = uuidData.get("name").getAsString();

                        if ((webData != null) && (uuid != null)) {

                            linebreaker();
                            System.out.println("Name: " + name);
                            System.out.println("UUID: " + uuid);
                            linebreaker();

                            Thread.sleep(10000);

                        } else {

                            linebreaker();
                            System.out.println("ERROR: Data could not be found. Please re-run the application.");
                            linebreaker();

                            Thread.sleep(10000);

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    break;

                case "PAST":

                    System.out.println("Enter a user's name: ");
                    input = scanner.nextLine();

                    try {

                        String webData = readUrl("https://api.mojang.com/users/profiles/minecraft/" + input);

                        Gson gson = new Gson();

                        JsonObject uuidData = gson.fromJson(webData, JsonObject.class);

                        String uuid = uuidData.get("id").getAsString();

                        String namesData = readUrl("https://api.mojang.com/user/profiles/"+uuid+"/names");

                        JsonArray pastNames = gson.fromJson(namesData, JsonArray.class);
                        
                        if ((input != null) && (uuid != null) && (pastNames != null)) {

                            linebreaker();
                            System.out.println("Previous names of [" + input + "] - (" + uuid + ") " + "Are: \n");

                            Iterator<JsonElement> iterator = pastNames.iterator();
                            while (iterator.hasNext()) {

                                JsonElement element = gson.fromJson(iterator.next(), JsonElement.class);
                                JsonObject nameObj = element.getAsJsonObject();
                                String name = nameObj.get("name").getAsString();

                                System.out.println(name + "\n");

                            }
                            linebreaker();

                            Thread.sleep(10000);

                        } else {

                            linebreaker();
                            System.out.println("ERROR: Data could not be found. Please re-run the application.");
                            linebreaker();

                            Thread.sleep(10000);

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    break;

            }

        } else {

            try {

                System.out.println("You had to enter 'CURRENT' or 'PAST' to view details. Please re-run the application!");
                Thread.sleep(10000);

            } catch (Exception exc) {

                exc.printStackTrace();

            }
        }
    }

    public static void linebreaker() {

        System.out.println("===== [MC INFO] =====");

    }

    private static String readUrl(String urlString) throws Exception {

        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) buffer.append(chars, 0, read);
            return buffer.toString();

        } finally {
            if (reader != null) reader.close();
        }
    }
}