package de.schoko.jamegam25;

import de.schoko.rendering.Graph;
import de.schoko.rendering.shapes.Circle;

public class Bullet extends GameObject{
    private double direction;
    private double speed;

    public Bullet(double x, double y, double direction) {
        super(x, y);
        this.direction = direction;
        this.speed = 7;
    }

    @Override
    public void render(Graph g, double deltaTimeMS) {
        this.x += Math.cos(direction) * deltaTimeMS / 1000 * speed;
        this.y += Math.sin(direction) * deltaTimeMS / 1000 * speed;

        g.draw(new Circle(x, y, 0.1));
    }
    
}
