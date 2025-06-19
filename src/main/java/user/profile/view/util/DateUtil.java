package user.profile.view.util;

import lombok.experimental.UtilityClass;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class DateUtil {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public String formatDate(long millis) {
        return DATE_FORMATTER.format(new Date(millis));
    }
}
