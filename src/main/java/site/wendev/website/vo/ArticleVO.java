package site.wendev.website.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleVO {
    private String title;
    private Long typeId;
}
