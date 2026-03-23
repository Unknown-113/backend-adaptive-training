package cz.cyberrange.platform.training.adaptive.api.dto.training;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@ApiModel(
        value = "TaskUpdateDTO"
)
public class TaskUpdateDTO {

    @ApiModelProperty(value = "Main identifier of the task", required = true, example = "1")
    @NotNull(message = "{task.id.NotNull.message}")
    private Long id;
    @ApiModelProperty(value = "Short description of task", required = true, example = "Task title")
    @NotEmpty(message = "{task.title.NotEmpty.message}")
    private String title;
    @ApiModelProperty(value = "The information that are displayed to a player", required = true, example = "Capture the flag", position = 1)
    @NotEmpty(message = "{task.content.NotEmpty.message}")
    private String content;
    @ApiModelProperty(value = "Keyword that must be found in the task. Necessary in order to get to the next phase", required = true, example = "secretFlag", position = 2)
    @Size(max = 50, message = "{task.answer.Size.message}")
    private String answer;
    @ApiModelProperty(value = "If true, the expected answer is generated dynamically from the configured secret and interval.", example = "true", position = 3)
    private boolean dynamicFlagEnabled;
    @ApiModelProperty(value = "Flag rotation interval in minutes when dynamic flag is enabled.", example = "15", position = 4)
    @Min(value = 1, message = "Dynamic flag interval must be at least 1 minute.")
    private Integer dynamicFlagIntervalMinutes;
    @ApiModelProperty(value = "Secret used to generate the dynamic flag.", example = "super-secret-value", position = 5)
    @Size(max = 255, message = "Dynamic flag secret must be at most 255 characters.")
    private String dynamicFlagSecret;
    @ApiModelProperty(value = "Description how to get the answer", required = true, example = "Open secret.txt", position = 3)
    @NotEmpty(message = "{task.solution.NotEmpty.message}")
    private String solution;
    @ApiModelProperty(value = "It defines the allowed number of incorrect answers submitted by the player", required = true, example = "5", position = 4)
    @NotNull(message = "{task.incorrectAnswerLimit.NotNull.message}")
    @Min(value = 0, message = "{task.incorrectAnswerLimit.Min.message}")
    @Max(value = 100, message = "{task.incorrectAnswerLimit.Max.message}")
    private Integer incorrectAnswerLimit;
    @ApiModelProperty(value = "It defines whether the sandbox can be modified", example = "true", position = 5)
    private boolean modifySandbox;
    @ApiModelProperty(value = "It defines the expected duration of sandbox change defined in seconds", example = "15", position = 1)
    @Min(value = 0, message = "{task.sandboxChangeExpectedDuration.Min.message}")
    private int sandboxChangeExpectedDuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isDynamicFlagEnabled() {
        return dynamicFlagEnabled;
    }

    public void setDynamicFlagEnabled(boolean dynamicFlagEnabled) {
        this.dynamicFlagEnabled = dynamicFlagEnabled;
    }

    public Integer getDynamicFlagIntervalMinutes() {
        return dynamicFlagIntervalMinutes;
    }

    public void setDynamicFlagIntervalMinutes(Integer dynamicFlagIntervalMinutes) {
        this.dynamicFlagIntervalMinutes = dynamicFlagIntervalMinutes;
    }

    public String getDynamicFlagSecret() {
        return dynamicFlagSecret;
    }

    public void setDynamicFlagSecret(String dynamicFlagSecret) {
        this.dynamicFlagSecret = dynamicFlagSecret;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Integer getIncorrectAnswerLimit() {
        return incorrectAnswerLimit;
    }

    public void setIncorrectAnswerLimit(Integer incorrectAnswerLimit) {
        this.incorrectAnswerLimit = incorrectAnswerLimit;
    }

    public boolean isModifySandbox() {
        return modifySandbox;
    }

    public void setModifySandbox(boolean modifySandbox) {
        this.modifySandbox = modifySandbox;
    }

    public int getSandboxChangeExpectedDuration() {
        return sandboxChangeExpectedDuration;
    }

    public void setSandboxChangeExpectedDuration(int sandboxChangeExpectedDuration) {
        this.sandboxChangeExpectedDuration = sandboxChangeExpectedDuration;
    }

    @AssertTrue(message = "Static answer is required when dynamic flag is disabled, and dynamic flag requires both secret and interval.")
    public boolean isAnswerConfigurationValid() {
        if (dynamicFlagEnabled) {
            return dynamicFlagIntervalMinutes != null
                    && dynamicFlagIntervalMinutes > 0
                    && dynamicFlagSecret != null
                    && !dynamicFlagSecret.isBlank();
        }
        return answer != null && !answer.isBlank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskUpdateDTO that = (TaskUpdateDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }


    @Override
    public String toString() {
        return "TaskUpdateDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", dynamicFlagEnabled=" + dynamicFlagEnabled +
                ", dynamicFlagIntervalMinutes=" + dynamicFlagIntervalMinutes +
                ", solution='" + solution + '\'' +
                ", incorrectAnswerLimit=" + incorrectAnswerLimit +
                ", modifySandbox=" + modifySandbox +
                ", sandboxChangeExpectedDuration=" + sandboxChangeExpectedDuration +
                '}';
    }
}
