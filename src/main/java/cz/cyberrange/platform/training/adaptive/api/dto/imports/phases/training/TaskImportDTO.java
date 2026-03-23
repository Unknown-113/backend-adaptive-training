package cz.cyberrange.platform.training.adaptive.api.dto.imports.phases.training;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Encapsulates information about training phase. Inherits from {@link TaskImportDTO}
 */
@ApiModel(
        value = "TaskImportDTO",
        description = "Imported task of the training phase."
)
@JsonIgnoreProperties({"id"})
public class TaskImportDTO {

    @ApiModelProperty(value = "Short textual description of the phase.", example = "Training phase title")
    @NotEmpty(message = "{task.title.NotEmpty.message}")
    protected String title;
    @ApiModelProperty(value = "Order of phase, starts with 0", example = "2", position = 1)
    @NotNull(message = "{task.order.NotNull.message}")
    @Min(value = 0, message = "{task.order.Min.message}")
    protected Integer order;
    @ApiModelProperty(value = "The information and experiences that are directed towards a participant.", example = "Play me", position = 2)
    @NotEmpty(message = "{task.content.NotEmpty.message}")
    private String content;
    @ApiModelProperty(value = "Keyword found in training, used for access next phase.", example = "secretFlag", position = 3)
    @Size(max = 50, message = "{task.answer.Size.message}")
    private String answer;
    @ApiModelProperty(value = "If true, the expected answer is generated dynamically from the configured secret and interval.", example = "true", position = 4)
    private boolean dynamicFlagEnabled;
    @ApiModelProperty(value = "Flag rotation interval in minutes when dynamic flag is enabled.", example = "15", position = 5)
    @Min(value = 1, message = "Dynamic flag interval must be at least 1 minute.")
    private Integer dynamicFlagIntervalMinutes;
    @ApiModelProperty(value = "Secret used to generate the dynamic flag.", example = "super-secret-value", position = 6)
    @Size(max = 255, message = "Dynamic flag secret must be at most 255 characters.")
    private String dynamicFlagSecret;
    @ApiModelProperty(value = "Instruction how to get answer in training.", example = "This is how you do it", position = 4)
    @NotEmpty(message = "{task.solution.NotEmpty.message}")
    private String solution;
    @ApiModelProperty(value = "How many times player can submit incorrect answer before displaying solution.", example = "5", position = 5)
    @NotNull(message = "{task.incorrectAnswerLimit.NotNull.message}")
    @Min(value = 0, message = "{task.incorrectAnswerLimit.Min.message}")
    @Max(value = 100, message = "{task.incorrectAnswerLimit.Max.message}")
    private int incorrectAnswerLimit;
    @ApiModelProperty(value = "Sign if sandbox should be modified if the task is picked.", example = "true", position = 6)
    private boolean modifySandbox;
    @ApiModelProperty(value = "Expected duration of the sandbox change when the task is picked (in seconds).", example = "30", position = 7)
    @Min(value = 0, message = "{task.sandboxChangeExpectedDuration.Min.message}")
    private int sandboxChangeExpectedDuration;

    /**
     * Gets answer.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets answer.
     *
     * @param answer the answer
     */
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

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets solution.
     *
     * @return the solution
     */
    public String getSolution() {
        return solution;
    }

    /**
     * Sets solution.
     *
     * @param solution the solution
     */
    public void setSolution(String solution) {
        this.solution = solution;
    }

    /**
     * Gets incorrect answer limit.
     *
     * @return the incorrect answer limit
     */
    public int getIncorrectAnswerLimit() {
        return incorrectAnswerLimit;
    }

    /**
     * Sets incorrect answer limit.
     *
     * @param incorrectAnswerLimit the incorrect answer limit
     */
    public void setIncorrectAnswerLimit(int incorrectAnswerLimit) {
        this.incorrectAnswerLimit = incorrectAnswerLimit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
    public String toString() {
        return "TaskImportDTO{" +
                "title='" + title + '\'' +
                ", answer='" + answer + '\'' +
                ", dynamicFlagEnabled=" + dynamicFlagEnabled +
                ", dynamicFlagIntervalMinutes=" + dynamicFlagIntervalMinutes +
                ", content='" + content + '\'' +
                ", solution='" + solution + '\'' +
                ", incorrectAnswerLimit=" + incorrectAnswerLimit +
                ", modifySandbox=" + modifySandbox +
                ", sandboxChangeExpectedDuration=" + sandboxChangeExpectedDuration +
                ", order=" + order +
                '}';
    }
}
