package VPLibrary.form;

public class FileField extends Field<String>{
	
    protected String[] extensions = {"jpg", "png", "gif", "jpeg"};
    protected String destFolder = ".";
	public FileField(String label, String name)
    {
		super(label, name);
             
    }

    public String[] getExtensions() {
		return extensions;
	}

	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
	}

	public String getDestFolder() {
		return destFolder;
	}

	public void setDestFolder(String destFolder) {
		this.destFolder = destFolder;
	}

    public boolean uploadFile()
    {
    	/*
        if($key == null)
            $key = $this->name;
        if(isset($_FILES[$key]["name"]) && $_FILES[$key]["name"] != ""){ 
            $pathinfo = pathinfo($_FILES[$key]["name"]);
            $extension = strtolower($pathinfo["extension"]);
            if(in_array($extension, $this->getExtensions())){
                $entireName = $this->getNamePrefix().$newName.$this->getNameSuffix();
                if(strpos($entireName, "/")> 0 && !file_exists($this->getDestFolder()."/".substr($entireName, 0, strpos($entireName, "/"))))
                    mkdir($this->getDestFolder()."/".substr($entireName, 0, strpos($entireName, "/")), 0777, true);
                $newBaseName = ($newName == null) ? $_FILES[$key]["name"] : $this->getNamePrefix().$newName.$this->getNameSuffix().".".$extension;
                if(move_uploaded_file($_FILES[$key]["tmp_name"], $this->getDestFolder()."/$newBaseName")){
                    $save = $this->getSave();
                    if($save != null){
                        if($object != null){
                            $method = "set".ucfirst($this->name);
                            switch($save){
                                case "all_raw": $object->$method($pathinfo["basename"]);break;
                                case "all_new": $object->$method($newBaseName);break;
                                case "name_raw": $object->$method($pathinfo["filename"]);break;
                                case "name_new": $object->$method($newName);break;
                                case "extension": $object->$method($extension);break;
                                default: $object->$method($pathinfo["basename"]);break;
                            }
                        }else{
                            throw new \Exception("Impossible d'effectuer la sauvegarde car aucun object n'a �t� fourni");
                        }
                    }
                    return true;        
                }
            }
        }
        return false;
        */
    	return true;
    }

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		return true;
	}
    
   
}
