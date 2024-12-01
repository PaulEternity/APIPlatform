package com.paul.paulapiclientsdk.model.params;

import com.paul.paulapiclientsdk.model.request.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class HoroscopeParams implements Serializable {
    private static final long serialVersionUID = 5815244709041840505L;
    /**
     * 十二星座对应英文小写，aries, taurus, gemini, cancer, leo, virgo, libra, scorpio, sagittarius, capricorn, aquarius, pisces
     */
    private String type;
    /**
     * 十二星座对应英文小写，aries, taurus, gemini, cancer, leo, virgo, libra, scorpio, sagittarius, capricorn, aquarius, pisces
     */
    private String time;
}
