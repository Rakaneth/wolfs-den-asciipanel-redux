package com.rakaneth.wolfsden.entity;

import com.rakaneth.wolfsden.GameContext;
import squidpony.squidmath.Coord;

import java.util.Optional;

public class MoveByCommand implements Command {
    private int dx;
    private int dy;

    public MoveByCommand(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override public CommandResult execute(GameObject entity) {
        Coord newCoord = entity.getPos()
                               .translate(dx, dy);
        Optional<GameObject> blocker = GameContext.getInstance()
                                                  .getBlockerAt(newCoord);
        boolean mapBlocked = entity.getMap()
                                   .blockMove(newCoord);

        if (blocker.isPresent()) {
            return new AlternateCommand(new InteractCommand(blocker.get()));
        } else if (!mapBlocked) {
            entity.move(newCoord);
            int timeTaken = Math.max(10 - entity.getStatValue("spd") / 10, 1);
            return new Done(timeTaken);
        } else {
            return new AlternateCommand(new WaitCommand());
        }
    }
}
