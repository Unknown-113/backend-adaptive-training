package cz.cyberrange.platform.training.adaptive.api.dto.export.phases.training;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        value = "TaskExportDTO"
)
public class TaskExportDTO {

    @ApiModelProperty(value = "Short description of task", required = true, example = "Task title")
    private String title;
    @ApiModelProperty(value = "Order of task in the training phase", required = true, example = "1")
    private Integer order;
    @ApiModelProperty(value = "The information that are displayed to a player", required = true, example = "Capture the flag")
    private String content;
    @ApiModelProperty(value = "Keyword that must be found in the task. Necessary in order to get to the next phase", required = true, example = "secretFlag")
    private String answer;
    @ApiModelProperty(value = "If true, the expected answer is generated dynamically from the configured secret and interval.", example = "true")
    private boolean dynamicFlagEnabled;
    @ApiModelProperty(value = "Flag rotation interval in minutes when dynamic flag is enabled.", example = "15")
    private Integer dynamicFlagIntervalMinutes;
    @ApiModelProperty(value = "Secret used to generate the dynamic flag.", example = "super-secret-value")
    private String dynamicFlagSecret;
    @ApiModelProperty(value = "Description how to get the answer", required = true, example = "Open secret.txt")
    private String solution;
    @ApiModelProperty(value = "It defines the allowed number of incorrect answers submitted by the player", required = true, example = "5")
    private int incorrectAnswerLimit;
    @ApiModelProperty(value = "It defines whether the sandbox can be modified", example = "true")
    private boolean modifySandbox;
    @ApiModelProperty(value = "It defines the expected duration of sandbox change defined in seconds", example = "15")
    private int sandboxChangeExpectedDuration;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public int getIncorrectAnswerLimit() {
        return incorrectAnswerLimit;
    }

    public void setIncorrectAnswerLimit(int incorrectAnswerLimit) {
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

    @Override
    public String toString() {
        return "TaskDTO{" +
                "title='" + title + '\'' +
                ", order=" + order +
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
