package app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Todo implements Serializable {
    @Id
    private String id;

    @Transient
    private boolean selected;

    private LocalDateTime createdAt;
    private String title;
    private String body;
    private String author;

    private boolean closed = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}