
package download_manager_;

public class Files {
    private String name;
    private String size;
    private String progress;
    private String url;
    private String dist;
    private int id;
    public Files(String name,String size,String progress,String url,String dist,int id){
        this.name= name;
        this.size = size;
        this.progress = progress;
        this.url = url;
        this.dist=dist;
        this.id=id;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name= name;
	}

    public String getSize() {
        return size;
    }

    public String getProgress() {
        return progress;
    }

    public String getUrl() {
        return url;
    }

    public void setSize(String size) {this.size = size;}

    public String getDist() {return dist;}

    public void setDist(String dist) {this.dist = dist;}

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}
