package net.honux.neko;

public class Neko extends BxObject {

    public Neko(Box box, int delay, int scale) {
        super(box, delay, scale);
        setStatus(CatStatus.STAND.toString(), 0);
    }

    //Neko class test
    public static void main(String[] args) {
        Neko neko = new Neko(null, 3, 1);
        for (int i = 0; i < 30; i++) {
            System.out.println(neko.getImage(i));
        }
    }

    @Override
    public void addStatusForImages() {
        for (var status : CatStatus.values()) {
            addStatus(status.toString());
        }
    }

    @Override
    public void addStatusImages() {
        setStatusImages(CatStatus.STAND.toString(), 25, 26);
        setStatusImages(CatStatus.SLEEPY.toString(), 27, 28);
        setStatusImages(CatStatus.SLEEP.toString(), 29, 30);
        setStatusImages(CatStatus.WAKE_UP.toString(), 31, 32);
        setStatusImages(CatStatus.TOP.toString(), 1, 2);

    }

    @Override
    public void update(int frame) {
        x += dx;
        y += dy;

        if (!checkStatus(CatStatus.SLEEP) && !checkStatus(CatStatus.SLEEPY) && box.getCoins().isEmpty()) {
            setStatus(CatStatus.SLEEPY.toString(), frame);
        }

        if(checkStatus(CatStatus.SLEEP) && !box.getCoins().isEmpty()) {
            setStatus(CatStatus.WAKE_UP.toString(), frame);
        }

        if (checkStatus(CatStatus.SLEEPY) && checkFrame(frame, getStatusImageSize(CatStatus.SLEEPY.toString()))) {
            setStatus(CatStatus.SLEEP.toString(), frame);
        }

        if (checkStatus(CatStatus.WAKE_UP) && checkFrame(frame, getStatusImageSize(CatStatus.WAKE_UP.toString()))) {
            setStatus(CatStatus.STAND.toString(), frame);
        }

        System.out.printf("Neko: %s\n", status);
    }

    private boolean checkFrame(int frame, int time) {
        return frame > this.getLastFrame() + delay * time;
    }

    private boolean checkStatus(CatStatus status) {
        return this.status == status.toString();
    }
}
