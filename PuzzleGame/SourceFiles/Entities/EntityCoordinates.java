package PuzzleGame.SourceFiles.Entities;

import java.awt.geom.Point2D;

class EntityCoordinates {

    private final Point2D playerXY, starXY;
    private final Star star;

    EntityCoordinates(final Point2D playerXY, final Star star, final Point2D starXY) {

        this.playerXY = playerXY;
        this.star = star;
        this.starXY = starXY;

    }

    Point2D getPlayerXY() {

        return playerXY;

    }

    Star getStar() {

        return star;

    }

    Point2D getStarXY() {

        return starXY;
        
    }

}