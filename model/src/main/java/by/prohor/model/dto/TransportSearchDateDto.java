package by.prohor.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * Created by Artsiom Prokharau 20.03.2021
 */

public class TransportSearchDateDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date should not be empty")
    @PastOrPresent(message="Incorrect value. Date can not be future")
    private Date dateBefore;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date should not be empty")
    @PastOrPresent(message="Incorrect value. Date can not be future")
    private Date dateAfter;

    public TransportSearchDateDto() {
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }
}
