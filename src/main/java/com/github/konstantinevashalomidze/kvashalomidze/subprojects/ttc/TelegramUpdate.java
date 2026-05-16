package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Telegram Update object.
 * Only fields relevant to this bot are mapped — unknown fields are ignored by Jackson.
 *
 * https://core.telegram.org/bots/api#update
 */
public record TelegramUpdate(

        @JsonProperty("update_id")
        long updateId,

        /**
         * New incoming message (text, location, etc.)
         */
        Message message,

        /**
         * Fired when a user edits a previously sent message.
         * Critically: LIVE LOCATION updates arrive here, not in `message`.
         */
        @JsonProperty("edited_message")
        Message editedMessage,

        /**
         * User pressed an inline keyboard button.
         */
        @JsonProperty("callback_query")
        CallbackQuery callbackQuery

) {
    /**
     * Returns whichever message is present — regular or edited.
     * Useful so handlers don't have to check both every time.
     */
    public Message anyMessage() {
        return message != null ? message : editedMessage;
    }

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#message
     */
    public record Message(

            @JsonProperty("message_id")
            long messageId,

            Chat chat,
            User from,
            String text,
            Location location,

            /**
             * Present when a user forwards a message.
             */
            @JsonProperty("forward_from")
            User forwardFrom,

            /**
             * The bot was added to or removed from a group.
             */
            @JsonProperty("new_chat_members")
            java.util.List<User> newChatMembers,

            @JsonProperty("left_chat_member")
            User leftChatMember,

            /**
             * Present when the message contains a venue (place pin).
             */
            Venue venue,

            /**
             * Contact shared by the user.
             */
            Contact contact,

            /**
             * Sticker sent.
             */
            Sticker sticker,

            @JsonProperty("date")
            long date
    ) {}

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#chat
     */
    public record Chat(

            long id,

            /**
             * "private", "group", "supergroup", or "channel"
             */
            String type,

            @JsonProperty("first_name")
            String firstName,

            @JsonProperty("last_name")
            String lastName,

            String username,
            String title
    ) {}

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#user
     */
    public record User(

            long id,

            @JsonProperty("is_bot")
            boolean isBot,

            @JsonProperty("first_name")
            String firstName,

            @JsonProperty("last_name")
            String lastName,

            String username,

            @JsonProperty("language_code")
            String languageCode
    ) {}

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#location
     *
     * Both static and live locations use this structure.
     * Live locations additionally carry accuracy and heading info.
     */
    public record Location(

            double latitude,
            double longitude,

            /**
             * Non-null for live locations: how many seconds the location will be updated.
             * Values: 60–86400.
             */
            @JsonProperty("live_period")
            Integer livePeriod,

            /**
             * Direction the user is moving, in degrees (1–360). Live locations only.
             */
            Integer heading,

            /**
             * Radius of uncertainty for the location, in metres. Live locations only.
             */
            @JsonProperty("horizontal_accuracy")
            Double horizontalAccuracy
    ) {
        /** True if this is a live (continuously updated) location. */
        public boolean isLive() {
            return livePeriod != null;
        }
    }

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#venue
     */
    public record Venue(
            Location location,
            String title,
            String address,

            @JsonProperty("foursquare_id")
            String foursquareId
    ) {}

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#contact
     */
    public record Contact(

            @JsonProperty("phone_number")
            String phoneNumber,

            @JsonProperty("first_name")
            String firstName,

            @JsonProperty("last_name")
            String lastName,

            @JsonProperty("user_id")
            Long userId
    ) {}

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#sticker
     * Minimal — just enough to detect and ignore stickers gracefully.
     */
    public record Sticker(
            @JsonProperty("file_id")
            String fileId,

            String emoji
    ) {}

    // -------------------------------------------------------------------------

    /**
     * https://core.telegram.org/bots/api#callbackquery
     *
     * Fired when a user taps an inline keyboard button.
     */
    public record CallbackQuery(

            String id,
            User from,
            Message message,

            /**
             * The data string set on the button when the inline keyboard was built.
             */
            String data
    ) {}
}