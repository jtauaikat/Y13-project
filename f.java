import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleButton extends JButton {

    private List<Particle> particles;
    private Timer timer;

    public ParticleButton(String text) {
        super(text);
        particles = new ArrayList<>();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop the current timer if running
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                // Clear the existing particles
                particles.clear();
                // Trigger the particle effects on button press
                createParticles(50); // Create 50 particles
            }
        });
    }

    private void createParticles(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = getWidth() / 2;
            int y = getHeight() / 2;
            int size = rand.nextInt(5) + 3; // Random size between 3 and 7 pixels
            int speedX = (rand.nextInt(5) + 2) * (rand.nextBoolean() ? 1 : -1); // Random horizontal speed between -6 and 6 pixels per frame
            int speedY = -(rand.nextInt(8) + 8); // Random vertical speed between -8 and -1 pixels per frame
            Color color = new Color(0,0,0); // Random color

            Particle particle = new Particle(x, y, size, speedX, speedY, color);
            particles.add(particle);
        }

        // Start the animation timer
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateParticles();
                repaint();
            }
        });
        timer.start();
    }

    private void updateParticles() {
        // Update particle positions and remove expired particles
        for (Particle particle : particles) {
            if (particle.getY() >= ParticleButton.this.getHeight() || particle.getLifespan() <= 0) {
                // Reset the vertical speed to 0 when the particle reaches the bottom or its lifespan expires
                particle.setSpeedY(0);
            } else {
                // Apply gravity to simulate falling
                particle.setSpeedY(particle.getSpeedY() + 1);
            }
            particle.update();
        }
        particles.removeIf(p -> p.getLifespan() <= 0 || p.getY() >= ParticleButton.this.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw particles
        for (Particle particle : particles) {
            g2d.setColor(particle.getColor());
            g2d.fillRect(particle.getX(), particle.getY(), particle.getSize(), particle.getSize());
        }

        g2d.dispose();
    }

    private class Particle {
        private int x;
        private int y;
        private int size;
        private int speedX;
        private int speedY;
        private int lifespan;
        private Color color;

        public Particle(int x, int y, int size, int speedX, int speedY, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speedX = speedX;
            this.speedY = speedY;
            this.lifespan = 100; // Set the lifespan of each particle
            this.color = color;
        }

        public void update() {
            x += speedX;
            y += speedY;
            lifespan--;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSize() {
            return size;
        }

        public Color getColor() {
            return color;
        }

        public int getLifespan() {
            return lifespan;
        }

        public int getSpeedY() {
            return speedY;
        }

        public void setSpeedY(int speedY) {
            this.speedY = speedY;
        }
    }
}
