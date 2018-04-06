package gamesys.automation.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import gamesys.automation.enums.OSType;
import gamesys.automation.enums.Result;
import gamesys.automation.tools.WebDriverUtils;

import static gamesys.automation.enums.Result.INFO;

public class Reporter
{
	private String reportPath;
	private String pictPath;
	private int counter;
	private int pictCounter;
	private String reportFile;
	Charset utf8 = StandardCharsets.UTF_8;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String BOLD = "\033[1m";
	public static final String UNBOLD = "\033[0m";


	public Reporter(String ReportName, String ReportPath)
	{
		try {
			OSType os = WebDriverUtils.getOperatingSystemType();

			reportPath = ReportPath;
			counter = 1;
			pictCounter = 1;
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("dd.MM_HH-mm");

			if (os == OSType.MacOS) {
				reportPath = reportPath + "/" + ft.format(dNow);
				pictPath = reportPath + "/" + "pict";
				reportFile = reportPath + "/" + "Report.html";
			} else {
				reportPath = reportPath + "\\" + ft.format(dNow);
				pictPath = reportPath + "\\pict";
				reportFile = reportPath + "\\Report.html";
			}


			Path path = Paths.get(reportPath);
			Files.createDirectories(path);
			path = Paths.get(pictPath);
			Files.createDirectories(path);


			File file = new File(reportFile);
			file.createNewFile();
			String content = "<html>\r\n"
                    + "<head>\r\n"
                    + "<style id=\"mceDefaultStyles\" type=\"text/css\">\r\n"
                    + ".mce-content-body div\r\n"
                    + ".mce-resizehandle {position: absolute;border: 1px solid black;box-sizing: box-sizing;background: #FFF;width: 7px;height: 7px;z-index: 10000}\r\n"
                    + ".mce-content-body \r\n"
                    + ".mce-resizehandle:hover {background: #000}\r\n"
                    + ".mce-content-body img[data-mce-selected],\r\n"
                    + ".mce-content-body hr[data-mce-selected] {outline: 1px solid black;resize: none}\r\n"
                    + ".mce-content-body .mce-clonedresizable {position: absolute;outline: 1px dashed black;opacity: .5;filter: alpha(opacity=50);z-index: 10000}\r\n"
                    + ".mce-content-body .mce-resize-helper {background: #555;background: rgba(0,0,0,0.75);border-radius: 3px;border: 1px;color: white;display: none;font-family: sans-serif;font-size: 12px;white-space: nowrap;line-height: 14px;margin: 5px 10px;padding: 5px;position: absolute;z-index: 10001}\r\n"
                    + "\r\n" + ".editorDemoTable {background-color: #FFF8C9; border-spacing: 0;}\r\n"
                    + ".editorDemoTable td{border: 1px solid #777; margin: 0 !important; padding: 2px 3px;}\r\n"
                    + ".editorDemoTable thead {background-color: #2E6C80; color: #FFF;}\r\n"
                    + ".editorDemoTable td:nth-child(3){text-align: center;}\r\n"
                    + ".editorDemoTable thead td {font-weight: bold;font-size: 13px;}\r\n"
                    + "\r\n"
                    + ".divTable{display: table;width: 100%;}\r\n"
                    + ".divTableRow {display: table-row;}\r\n"
                    + ".divTableHeading {display: table-header-group;background-color: #ddd;}\r\n"
                    + ".divTableCell, \r\n"
                    + ".divTableHead {display: table-cell;	padding: 3px 10px;	border: 1px solid #999999;}\r\n"
                    + ".divTableHeading {	display: table-header-group; background-color: #ddd; font-weight: bold;}\r\n"
                    + ".divTableFoot {display: table-footer-group;	font-weight: bold;background-color: #ddd;}\r\n"
                    + ".divTableBody {	display: table-row-group;}\r\n"
                    + "</style>\r\n"
                    + "\r\n"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
                    + "</head>\r\n"
                    + "<body id=\"tinymce\" class=\"mce-content-body \" data-id=\"elm1\" contenteditable=\"false\" spellcheck=\"false\">\r\n"
                    + "<h2 style=\"color: #2e6c80;\" data-mce-style=\"color: #2e6c80;\">" + ReportName + ":</h2>\r\n"
                    + "<table class=\"editorDemoTable mce-item-table\" style=\"height: 140px; width: 1249px;\" data-mce-style=\"height: 140px; width: 1249px;\" data-mce-selected=\"1\">\r\n"
                    + "<thead>\r\n"
                    + "<tr style=\"height: 16px;\" data-mce-style=\"height: 16px;\">\r\n"
                    + "<td style=\"width: 55px; height: 16px;\" data-mce-style=\"width: 55px; height: 16px;\">#</td>\r\n"
                    + "<td style=\"width: 79px; height: 16px;\" data-mce-style=\"width: 79px; height: 16px;\">Result</td>\r\n"
                    + "<td style=\"width: 750px; height: 16px;\" data-mce-style=\"width: 210px; height: 16px;\">Log message</td>\r\n"
                    + "<td style=\"width: 100px; height: 16px;\" data-mce-style=\"width: 453px; height: 16px;\">Screenshot</td>\r\n"

                    + "</tr>\r\n"
                    + "</thead>\r\n"
                    + "<tbody>\r\n"
                    + "\r\n"
                    + "</tbody></table></body></html>";

			Files.write(Paths.get(reportFile), content.getBytes(utf8));
		}
		catch(Exception e){}

	}

	public void Write(String message, Result res)
	{
		Write(message, res, null);
	}


	public void Write(String message, Result res,BufferedImage screenshot)
	{
		try
		{
			String content = new String(Files.readAllBytes(Paths.get(reportFile)));
			String picture = "";
			String result = "";
			switch (res)
			{
				case INFO:
					result = "<strong>info</strong>";
					break;
				case PASS:
					result = "<span style=\\\"color: #339966;\\\" data-mce-style=\\\"color: #339966;\\\"><strong>PASS</strong></span>";
					break;
				case FAIL:
					result = "<span style=\\\"color: #ff0000;\\\" data-mce-style=\\\"color: #ff0000;\\\"><strong>FAIL</strong></span>";
					break;
				case WARNING:
					result = "<span style=\\\"color: #ff6600;\\\" data-mce-style=\\\"color: #ff6600;\\\"><strong>WARNING</strong></span>";
					break;
			}

			if (screenshot != null) {
				String picturePath;

				picturePath = (WebDriverUtils.getOperatingSystemType() == OSType.MacOS) ?
						pictPath + "/" + String.valueOf(pictCounter) + ".png" :
						pictPath + "//" + String.valueOf(pictCounter) + ".png";


				File outputfile = new File(picturePath);
				Write("Screenshot: " + outputfile, INFO);
				ImageIO.write(screenshot, "png", outputfile);

				picture = "<br><a href=\\\"./pict/" + String.valueOf(pictCounter) + ".png\\\" target=\\\"_blank\\\">Screenshot</a>";
				pictCounter++;
			}

			//COMMENT FOR TEST
			String input = "<tr style=\"height: 27px;\" data-mce-style=\"height: 27px;\">\r\n"
					+ "<td style=\"width: 55px; text-align: center; height: 27px;\" data-mce-style=\"width: 55px; text-align: center; height: 27px;\">" + String.valueOf(counter) + "</td>\r\n"
                    + "<td style=\"width: 79px; height: 27px;\" data-mce-style=\"width: 79px; height: 27px;\">" + result + "</td>\r\n"
                    +"<td style=\"width: 210px; height: 27px;\" data-mce-style=\"width: 750px; height: 27px;\">" + message + "</td>\r\n"

					//+ "<td style=\"width: 453px; height: 27px;\" data-mce-style=\"width: 453px; height: 27px;\">" + expectedResult + "</td>\r\n"
					+ "<td style=\"width: 416px; height: 27px;\" data-mce-style=\"width: 100px; height: 27px;\">" + picture + "</td>\r\n"
                    + "</tr>\r\n\r\n" + "</tbody></table></body></html>";
			content = content.replaceAll("</tbody></table></body></html>", input);
			Files.write(Paths.get(reportFile), content.getBytes(utf8));


			String msg = new SimpleDateFormat("HH:mm:ss").format(new Date()) + " >>> " + ((res==INFO) ? String.valueOf(res).toLowerCase() : String.valueOf(res)) + " - " + message;

			switch (res)
			{
				case PASS:
					System.out.println(BOLD+ANSI_GREEN + msg + ANSI_RESET+UNBOLD);
					break;
				case FAIL:
					System.out.println(BOLD+ANSI_RED + msg + ANSI_RESET+UNBOLD);
					break;
				case WARNING:
					System.out.println(BOLD+ANSI_YELLOW + msg + ANSI_RESET+UNBOLD);
					break;
				default:
					System.out.println(msg);
					break;
			}

			counter++;
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}


