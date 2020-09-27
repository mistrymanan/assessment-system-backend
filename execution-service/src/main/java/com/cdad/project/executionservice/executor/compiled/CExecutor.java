package com.cdad.project.executionservice.executor.compiled;

import com.cdad.project.executionservice.dto.ProgramInput;
import com.cdad.project.executionservice.dto.TestInput;
import com.cdad.project.executionservice.dto.TestOutput;
import com.cdad.project.executionservice.entity.Status;
import com.cdad.project.executionservice.exceptions.CompilationErrorException;
import com.cdad.project.executionservice.executor.BaseExecutor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class CExecutor extends BaseExecutor implements CompiledExecutor {
    public CExecutor(ProgramInput program) throws IOException, InterruptedException {
        super(program);
        if (!this.compile().equals(0)) {
            setStatus(Status.COMPILE_ERROR);
        }
        List<String> command = new ArrayList<>();
        command.add("chroot /jail/");
        command.add("timeout " + this.getTimeout());
        command.add("." + getBaseExecutionPath() + "a.out");
        setRunCommandString(String.join(" ", command));
    }

    @Override
    public Integer compile() throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        //command.add("sudo chroot /jail/ echo");
        command.add("cd /jail" + getBaseExecutionPath() + "/ ");
        command.add("gcc Solution.c");
        ProcessBuilder processBuilder = new ProcessBuilder().command("/bin/bash", "-c", String.join(";", command))
                .directory(new File(getJailPath()))
                .redirectError(new File(this.getContainerPath() + "error.txt"));
        Process process = processBuilder.start();
        return process.waitFor();
    }

    @Override
    public TestOutput run(String testInput) throws InterruptedException, IOException, CompilationErrorException {
        return super.run(getRunCommandString(), testInput);
    }

    @Override
    public List<TestOutput> run(List<TestInput> testInputs) throws InterruptedException, IOException, CompilationErrorException {
        return super.run(getRunCommandString(), testInputs);
    }


}
