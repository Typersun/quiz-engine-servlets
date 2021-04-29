package dto.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorEntity {
    // Общие ошибки
    INVALID_REQUEST(400, "Неверный запрос"),
    INVALID_TOKEN(403, "Ошибка авторизации"),
    NOT_FOUND(404, "Не найдено"),
    INVALID_NAME(450, "Неверное имя"),
    INVALID_PHONE(451, "Введите корректный телефон"),
    PHONE_ALREADY_TAKEN(452, "Телефон уже занят"),
    USERNAME_ALREADY_TAKEN(453, "Username уже занят"),

    // Регистрация
    PASSWORD_TOO_SHORT(460, "Пароль слишком короткий"),
    INVALID_USERNAME(461, "Некорректный username"),

    // Вход
    USER_NOT_FOUND(404,"Пользователь не найден"),
    INCORRECT_PASSWORD(460, "Неверный пароль"),

    // Выгрузка картинки
    ONLY_IMAGES_AVAILABLE_TO_UPLOAD(460, "Выгружать можно только картинки"),
    ;

    int status;
    String message;

    ErrorEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
