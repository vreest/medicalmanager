package com.medicalmanager.controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import com.medicalmanager.models.Patient;
import com.medicalmanager.views.PatientView;

/**
 * Allows patients to be printed out to physical copies on a physical 
 * printer.
 * 
 * @school: Annandale High School
 * @IDE: Eclipse
 * @date: 3/6/2013
 * @author Ben Vest
 *
 */
public class Print implements Printable {
	boolean all = false;
	
	public Print(boolean all){
		this.all = all;
	}
	
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		if (pageIndex != 0){
			return NO_SUCH_PAGE;
		}
		int y = 200;
		int x = 100;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setFont(new Font("Serif", Font.PLAIN, 36));
		g2.setPaint(Color.black);
		g2.drawString("Medical Manager Patient Printout", 50, 100);
		
		g2.setFont(new Font("Serif", Font.PLAIN, 24));
		if(all){
			for(Patient p: PatientView.getPatientArray()){
				g2.drawString("-----------------------", x, y);
				y += 50;
				g2.drawString("Name: " + p.getFullName(), x, y);
				y += 50;
				g2.drawString("Age: " + p.getAge(), x, y);
				y += 50;
				g2.drawString("Email: " + p.getEmailAddress(), x, y);
				y += 50;
				g2.drawString("-----------------------", x, y);
				y += 100;
			}
		} else {
		    Patient p = PatientView.getSelected();
			g2.drawString("Name: " + p.getFullName(), x, y);
			y += 50;
			g2.drawString("Age: " + p.getAge(), x, y);
			y += 50;
			g2.drawString("Email: " + p.getEmailAddress(), x, y);
			y += 100;
		}
		
		Rectangle2D outline = new Rectangle2D.Double(pf.getImageableX(), 
				pf.getImageableY(), 
				pf.getImageableWidth(),
				pf.getImageableHeight());
		
		g2.draw(outline);
		
		return PAGE_EXISTS;
	  }
}