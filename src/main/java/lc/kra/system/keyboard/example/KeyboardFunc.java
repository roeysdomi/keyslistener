/**
 * Copyright (c) 2016 Kristian Kraljic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lc.kra.system.keyboard.example;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import com.sun.jndi.url.dns.dnsURLContext;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lock.Camera;
import lock.Node;
import lock.Textnow;

public class KeyboardFunc {
	public HashMap<String, Node> hmap = new HashMap<String, Node>();
    public String name ="";
    public int type=1;
	//----------------------------
	public   Queue<String[]> fifo = new CircularFifoQueue<String[]>(9);
	private static boolean run = true;
	public static void main(String[] args) {
		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
		KeyboardFunc ro=new KeyboardFunc();
	    ro.reckeyboard();
	    
	    ro.startkeyboard();
		
	}
	public void startkeyboard()
	{
		run=true;
			fifo=new CircularFifoQueue<String[]>(9);
			GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input

			System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");
			for(Entry<Long,String> keyboard:GlobalKeyboardHook.listKeyboards().entrySet())
				System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());
			
			keyboardHook.addKeyListener(new GlobalKeyAdapter() {
				@Override public void keyPressed(GlobalKeyEvent event) {
					//System.out.println(event);
					if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE)
						run = false;
				}
				@Override public void keyReleased(GlobalKeyEvent event) 
				{       
					
					fifo.add(keypress(event));
					String mako=getstr(fifo);
						if(mako.length()==9&hmap.containsKey(mako))
						{     
							
							
							if(hmap.get(mako).getTime()>timediff())
							{
								System.out.println("detect");
							}
							else
							{
								
								try 
								{
									Textnow r=new Textnow();
									r.sendmess(hmap.get(mako).getTextuser(), Camera.takepic());
									r.sendmess(hmap.get(mako).getTextuser(), "SOMEONE TRYING TO HACK YOU");
								} 
								catch (IOException e1)
								{
									// TODO Auto-generated catch block
									
								}
								
							}
							
						}
						
					
					 
				}
			});
		
			try {
				while(run) Thread.sleep(128);
			} catch(InterruptedException e) { /* nothing to do here */ }
			  finally { keyboardHook.shutdownHook(); }
		
	}
	public void reckeyboard()
	{
		run=false;
		run=true;
		fifo=new CircularFifoQueue<String[]>(9);
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input

		System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");
		for(Entry<Long,String> keyboard:GlobalKeyboardHook.listKeyboards().entrySet())
			System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());
		
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override public void keyPressed(GlobalKeyEvent event) {
				//System.out.println(event);
				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE)
					run = false;
			}
			@Override public void keyReleased(GlobalKeyEvent event)
			{       
				
				fifo.add(keypress(event));
				String mako=getstr(fifo);
					if(mako.length()==9)
					{     
						
						Node n=new Node(timediff(), type, name, "roey.sdomi");
						
						hmap.put(mako, n);
						keyboardHook.shutdownHook();
						System.out.println("done recoreding");
						System.out.println(n.getTime()+"  :"+mako);
						
						run=false;
						return;
						
					}
					
				
				 
			}
		});
	
		try {
			while(run) Thread.sleep(128);
		} catch(InterruptedException e) { /* nothing to do here */ }
		  finally { keyboardHook.shutdownHook(); }
	}
	public static String getstr(Queue<String[]> b)
	{
		String a="";
		
		for(String[] d:b)
		{
			a=a+d[1];
		}
		return a;
	}
	public static double gettime()
	{
		int a=Calendar.getInstance().get(Calendar.SECOND);
		int b=Calendar.getInstance().get(Calendar.MILLISECOND);
		double c=Double.valueOf(a+"."+b);
		return c;
		
	}
    public static String[] keypress(GlobalKeyEvent event)
    {
    	String a=String.valueOf(gettime());
		String b=String.valueOf(event.getKeyChar());
		String[]c= new String[2];
		c[0]=a;
		c[1]=b;
		return c;
    	
    }
    public  double timediff()
    {
    	int counter=1;
    	double a=0;
    	double b=0;
    	for(String []g:fifo)
    	{
    		if(counter==1) {a=Double.valueOf(g[0]);}
    		if(counter==9) {b=Double.valueOf(g[0]);}
    		counter++;
    	}
    	double c=0;
    	
    	if(b>a)
    	{c=a-b;}
    	
    	if(c<0) {c=c*-1;}
    	System.out.println(c);
    	
    	return c;
    }
    public static void startchrome() throws IOException {
		 Process p3t = Runtime
	                .getRuntime()
	                .exec("cmd /c start chrome.exe");
	}

}