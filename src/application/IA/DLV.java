package application.IA;

import application.controller.KeyHandler;
import application.model.GameStatus;
import application.model.Movement;
import application.model.ThreesBlock;
import application.util.Direction;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class DLV {

    private static final String LINUX_PATH = "lib/dlv-2.1.1-linux-x86_64";
    private static final String MACOS_PATH = "lib/dlv-2.1.1-macos-12.2";
    private static final String PROGRAM = "encodings/program";

    private static Handler handler;

    public DLV() {loop();}

    private void startDLV() {
        handler = new DesktopHandler(new DLV2DesktopService(LINUX_PATH));
        // usare la riga di sotto invece di quella di sopra per eseguire su MacOS
        //handler = new DesktopHandler(new DLV2DesktopService(MACOS_PATH));

        try {
            ASPMapper.getInstance().registerClass(ThreesBlock.class);
            ASPMapper.getInstance().registerClass(Movement.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            e.printStackTrace();
        }

        InputProgram facts = new ASPInputProgram();
        ThreesBlock[][] matrix = GameStatus.getInstance().getBlocks();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    facts.addObjectInput(matrix[i][j]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        handler.addProgram(facts);

        InputProgram encoding = new ASPInputProgram();
        encoding.addFilesPath(PROGRAM);
        handler.addProgram(encoding);

        Output o = handler.startSync();

        AnswerSets as = (AnswerSets) o;
        for(AnswerSet a : as.getOptimalAnswerSets()) {
            System.out.println(a.toString());
            try {
                for(Object obj : a.getAtoms()) {
                    if(obj instanceof Movement) {
                        Movement movement = (Movement) obj;
                        if(movement.getDirection() == Direction.NORTH) {
                            KeyHandler.getInstance().moveBoard(Direction.NORTH);
                        } else if(movement.getDirection() == Direction.SOUTH){
                            KeyHandler.getInstance().moveBoard(Direction.SOUTH);
                        } else if(movement.getDirection() == Direction.EAST){
                            KeyHandler.getInstance().moveBoard(Direction.EAST);
                        } else if(movement.getDirection() == Direction.WEST){
                            KeyHandler.getInstance().moveBoard(Direction.WEST);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loop() {
        while(!GameStatus.getInstance().isGameOver()) {
            try {
                startDLV();
                sleep(500); // default = 500
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
