package com.xmlmachines;


public class ClientBroken {
}
/*
 * private String host; private int port;
 * 
 * private TCPNIOConnectorHandler connector_handler; private ReadController
 * controller; private TCPNIOConnectorHandler tcp_selector_handler;
 * 
 * private final ByteBuffer buf = ByteBuffer.allocate(100);
 * 
 * public static void main(String[] args) { ClientBroken c = new ClientBroken();
 * c.execute(args); }
 * 
 * public void execute(String[] args) { long elapsed = 0; int repeats = 0; final
 * CountDownLatch started = new CountDownLatch(1);
 * 
 * for (int i = 0; i < args.length; i++) { if (args[i].equals("-h")) { host =
 * args[++i]; } else if (args[i].equals("-p")) { port =
 * Integer.parseInt(args[++i]); } else if (args[i].equals("-r")) { repeats =
 * Integer.parseInt(args[++i]); } } if (port == 0 || host == null) {
 * System.out.println("No port or host set"); System.exit(1); } if (repeats ==
 * 0) { System.out.println("No nr of repeats given. Will default to 10");
 * repeats = 10; }
 * 
 * controller = new Controller(); tcp_selector_handler = new
 * TCPNIOConnectorHandler(true);
 * controller.addSelectorHandler(tcp_selector_handler);
 * 
 * controller.addStateListener(new ControllerStateListenerAdapter() {
 * 
 * public void onException(Throwable e) {
 * System.out.println("Grizzly controller exception:" + e.getMessage()); }
 * 
 * public void onReady() { System.out.println("Ready!"); started.countDown(); }
 * 
 * });
 * 
 * new Thread(controller).start(); try { started.await(); } catch (Exception e)
 * { System.out.println("Timeout in wait" + e.getMessage()); }
 * 
 * connector_handler = (TCPConnectorHandler) controller
 * .acquireConnectorHandler(Controller.Protocol.TCP);
 * 
 * try { byte[] filler = new byte[92]; Arrays.fill(filler, (byte) 2);
 * 
 * // connector_handler.connect(new InetSocketAddress(host,port));
 * 
 * connector_handler.connect(new InetSocketAddress(host, port), new
 * CallbackHandler<Context>() {
 * 
 * public void onConnect(IOEvent<Context> e) { SelectionKey k =
 * e.attachment().getSelectionKey();
 * System.out.println("Callbackhandler: OnConnect..."); try {
 * connector_handler.finishConnect(k); } catch (Exception ex) { System.out
 * .println("exception in CallbackHandler:" + ex.getMessage()); }
 * e.attachment().getSelectorHandler() .register(k, SelectionKey.OP_READ); }
 * 
 * public void onRead(IOEvent<Context> e) { }
 * 
 * public void onWrite(IOEvent<Context> e) { }
 * 
 * });
 * 
 * int ctr = 0; while (ctr < repeats) { buf.putLong(System.nanoTime());
 * buf.put(filler); buf.flip(); long size = connector_handler.write(buf, true);
 * buf.clear(); connector_handler.read(buf, true); elapsed += System.nanoTime()
 * - buf.getLong(); buf.clear(); ctr++; } System.out.println("" + repeats +
 * " run at a total of " + elapsed / 1000 +
 * " microseconds. Per packet it comes down to " + (elapsed / repeats) / 1000 +
 * " microseconds per roundtrip."); connector_handler.close();
 * controller.stop(); } catch (Exception e) {
 * System.out.println("Exception in execute..." + e);
 * e.printStackTrace(System.out); } } }
 */