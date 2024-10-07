package pages;

import java.util.Random;

public class Data {

    public static String getCategoryJson(int id, String name) {
        return String.format("""
            {
                "id": %d,
                "name": "%s"
            }
        """, id, name);
    }

    public static String getTagJson(int id, String name) {
        return String.format("""
            {
                "id": %d,
                "name": "%s"
            }
        """, id, name);
    }

    public static String getPetRequestBody(int id, String name, String status, String category, String tag) {
        return String.format("""
            {
                "id": %d,
                "category": %s,
                "name": "%s",
                "photoUrls": [
                    "string"
                ],
                "tags": [
                    %s
                ],
                "status": "%s"
            }
        """, id, category, name, tag, status);
    }

    public static int getRandomId() {
        return new Random().nextInt(10000) + 1;
    }

    public static String getRandomStatus() {
        String[] statuses = {"available", "pending", "sold"};
        return statuses[new Random().nextInt(statuses.length)];
    }

}
