package vplibrary.form;


public class FileField extends Field<String>{
	
	/**
	 * Extensions acceptées
	 */
    protected String[] extensions = null;
    
    /**
     * Dossier de destination des fichiers
     */
    protected String destFolder = ".";
	public FileField(String label, String name, String destFolder, String[] extensions)
    {
		super(label, name);
		this.destFolder = destFolder;
		this.extensions = extensions;
             
    }
	public FileField(String label, String name, String destFolder)
    {
		this(label, name, destFolder, null);
		
    }

    public String[] getExtensions() {
		return extensions;
	}

	public String getDestFolder() {
		return destFolder;
	}
    
   
}
