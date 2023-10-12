/**
 * Particle class for creating and managing particles in the game.
 * Created by Joshua Toumu'a & Leo Riginelli
 * Date: 12/10/23
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {
    private int x;            // X-coordinate of the particle
    private int y;            // Y-coordinate of the particle
    private int vx;           // Velocity in the X direction
    private int vy;           // Velocity in the Y direction
    private int size;         // Size of the particle
    private Color color;      // Color of the particle
    private int lifespan;     // Remaining lifespan of the particle

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;

        Random rand = new Random();
        this.vx = rand.nextInt(5) - 2;    // Random X velocity between -2 and 2
        this.vy = rand.nextInt(5) - 2;    // Random Y velocity between -2 and 2
        this.size = rand.nextInt(5) + 2;  // Random size between 2 and 6

        this.color = new Color(200, 0, 0);  // Particle color (red)
        this.lifespan = 10 + rand.nextInt(20);  // Random lifespan between 10 and 29
    }

    public boolean isAlive() {
        return lifespan > 0;  // Check if the particle is still alive
    }

    public void update() {
        x += vx;         // Update X-coordinate based on velocity
        y += vy;         // Update Y-coordinate based on velocity
        lifespan--;      // Decrease the remaining lifespan
    }

    public void draw(Graphics g) {
        g.setColor(color);              // Set the particle color
        g.fillOval(x, y, size, size);   // Draw the particle as a filled oval
    }
}
