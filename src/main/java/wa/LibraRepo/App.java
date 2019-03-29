package wa.LibraRepo;

import gnu.getopt.Getopt;

public class App 
{
	static String projectID = "";
	static String any_pageID = "";
	static String any_guideline = "";
	static Boolean reset_guideline_flag = false;
	static Boolean show_help_flag = false;
	static Boolean args_flag = true;
	
    public static void main( String[] args )
    {
    	
    	//コマンドライン引数処理
    	Getopt options = new Getopt("App", args, "ht:p:g:o:");
    	int c;
    	while( (c = options.getopt()) != -1) {
    		switch(c) {
    		case 'p':
    			any_pageID = options.getOptarg();
    			break;
    		case 'g':
    			any_guideline = options.getOptarg();
    			break;
    		case 'h':
    			show_help_flag = true;
    			break;
    		case 'o':
    			String operation = options.getOptarg();
    			if(operation.equals("reset-guideline")) reset_guideline_flag = true;
    			break;
    		case 't':
    			projectID = options.getOptarg();
    		default:
    			break;
    		}
    	}
    	
    	if(projectID.equals("") && any_pageID.equals("") && any_guideline.equals("") && !reset_guideline_flag && !show_help_flag) {
    		//System.out.println("コマンドライン引数が指定されていません");
    		args_flag = false;
    	}
    	
    	if(args_flag) {
    		
    		//report処理
    		if(!projectID.equals("")) {
    			LibraRepoMain.exec(projectID, any_pageID, any_guideline);
    			
    		} else {
    			
    			//help処理
    			if(show_help_flag) {
    				System.out.println("ヘルプが実行される...予定");
    				
    			//guidelineデータリセット処理
    			} else if(reset_guideline_flag) {
    				System.out.println("ガイドラインデータリセット処理が実行される...予定");
    			}
    		}
    	}
    	
    }
}
