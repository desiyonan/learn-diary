import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;


/**
 * Xms1m Xmx1m
 */
class ReferenceTest{

    public static void main(String[] args) {
        // testNewStrongReference();
        // testNewSoftReference();
        // testNewWeakReference();
        testNewPhantomReference();
    }

    public static void testNewStrongReference() {
        Object obj = "str";
        System.out.println("bf gc : " + obj);
        System.gc();
        System.out.println("af gc : " + obj);

        int i = 0;
        List<Object> objs = new LinkedList<>();
        try{
            while(true){
                objs.add(i);
                System.out.print("now is : " + ++i + "\r");
            }
        }finally{
            System.out.println("bf size is : " + objs.size());
            System.gc();
            System.out.println("af size is : " + objs.size());
            objs = null;
            System.gc();
        }
    }

    public static void testNewSoftReference() {
        SoftReference<Object> sfrObj = new SoftReference<Object>(1);
        System.out.println("bf obj is : " + sfrObj.get());
        System.gc();
        System.out.println("af obj is : " + sfrObj.get());

        List<SoftReference<Object>> sfObjs = new LinkedList<>();
        int sz = 11000;
        for(int i = 0; i< sz; i++){
            sfObjs.add(new SoftReference<Object>(i));
            System.out.print("now is : " + i + "\r");
        }
        System.out.println("bf size is : " + sfObjs.size());
        // System.gc();
        List<Object> stObjs = new LinkedList<>();
        for(int i = 0; i< sz; i++){
            stObjs.add(i);
        } // 继续分配 触发一次GC
        sz = 0;

        for (SoftReference<Object> sfObj:sfObjs){
            if(null!= sfObj.get()){
                sz ++;
            }
        }
        System.out.println("af size is : " + sz);
    }

    public static void testNewWeakReference() {
        WeakReference<Object> wkrObj = new WeakReference<Object>(128); // [-128,127] 为静态数据，不会被回收
        System.out.println("bf obj is : " + wkrObj.get());
        System.gc();
        System.out.println("af obj is : " + wkrObj.get());
    }

    public static void testNewPhantomReference() {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phrObj = new PhantomReference<Object>(128, queue);

        new Thread(()->{
            while(true){
                PhantomReference<Object> ref =(PhantomReference<Object>) queue.poll();
                if (ref!=null){
                    System.out.println("gc had run, phrObj is :" + ref.get());
                    System.exit(1);
                }
            }
        }).start();

        new Thread(()->{
            List<Object> l = new LinkedList<>();
            try {
                while(true){
                    l.add(new byte[2048 * 100]);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }).start();
    }
}