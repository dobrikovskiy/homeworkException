import java.io.*;
import java.util.*;
import java.util.regex.*;

public class UserInfoApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия(строка) Имя(строка) Отчество(строка) дата_рождения(строка в формате dd.mm.yyyy) номер_телефона(целое беззнаковое число) пол(m или f)");
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        // Проверка количества введенных данных
        if (data.length != 6) {
            System.out.println("Ошибка (код ошибки 1): введено неверное количество данных. Требуется 6 параметров.");
            return;
        }

        try {
            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String dateOfBirth = data[3];
            String phoneNumber = data[4];
            String gender = data[5];

            // Проверка формата даты
            if (!isValidDate(dateOfBirth)) {
                throw new InvalidFormatException("Неверный формат даты (код ошибки 2). Ожидается dd.mm.yyyy");
            }

            // Проверка формата номера телефона
            if (!isValidPhoneNumber(phoneNumber)) {
                throw new InvalidFormatException("Неверный формат номера телефона (код ошибки 3). Ожидается целое беззнаковое число.");
            }

            // Проверка пола
            if (!gender.equals("f") && !gender.equals("m")) {
                throw new InvalidFormatException("Неверный формат пола (код ошибки 4). Ожидается 'f' или 'm'.");
            }

            // Запись в файл
            writeToFile(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
            System.out.println("Данные успешно сохранены.");
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        try {
            Long.parseLong(phoneNumber);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void writeToFile(String lastName, String firstName, String middleName, String dateOfBirth, String phoneNumber, String gender) throws IOException {
        String fileName = lastName + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(lastName + " " + firstName + " " + middleName + " " + dateOfBirth + " " + phoneNumber + " " + gender);
        writer.newLine();
        writer.close();
    }
}

class InvalidFormatException extends Exception {
    public InvalidFormatException(String message) {
        super(message);
    }
}
