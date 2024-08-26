package com.example.tapchikhcn.dto.search;
import com.example.tapchikhcn.constans.enums.LogStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogSearch extends SearchDto{
    private int id;
    private LogStatus status;
}
