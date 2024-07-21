package dgu.camputhon.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatioDTO {
    private double healthRatio;
    private double studyRatio;
    private double leisureRatio;
}
