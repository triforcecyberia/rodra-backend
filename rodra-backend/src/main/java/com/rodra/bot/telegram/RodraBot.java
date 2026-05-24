package com.rodra.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class RodraBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.chat.id}")
    private String chatId;

    public RodraBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    // Входящие сообщения боту не обрабатываем — он только отправляет
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long id = update.getMessage().getChatId();
            log.info("Получено сообщение от chat_id={}: {}", id, text);
        }
    }

    public void sendLead(String name, String phone, String service, String comment) {
        String text = buildMessage(name, phone, service, comment);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("HTML");

        try {
            execute(message);
            log.info("Заявка отправлена в Telegram: {}", phone);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки в Telegram: {}", e.getMessage());
        }
    }

    private String buildMessage(String name, String phone, String service, String comment) {
        StringBuilder sb = new StringBuilder();
        sb.append("🚗 <b>Новая заявка с сайта РОДРА!</b>\n\n");
        sb.append("👤 <b>Имя:</b> ").append(escape(name)).append("\n");
        sb.append("📞 <b>Телефон:</b> ").append(escape(phone)).append("\n");
        sb.append("🔧 <b>Услуга:</b> ").append(escape(service)).append("\n");

        if (comment != null && !comment.isBlank()) {
            sb.append("💬 <b>Комментарий:</b> ").append(escape(comment)).append("\n");
        }

        sb.append("\n⏰ Ответьте клиенту как можно скорее!");
        return sb.toString();
    }

    // Экранируем спецсимволы HTML
    private String escape(String text) {
        if (text == null) return "—";
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
