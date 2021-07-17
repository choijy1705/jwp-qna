package qna.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean deleted;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "writer_id")
    private Long writerId;



    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.writerId = writer.getId();
        this.questionId = question.getId();
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public void toQuestion(Question question) {
    }
}
