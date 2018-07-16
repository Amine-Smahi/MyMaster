namespace MyMaster
{
  public class OutputHelper
  {
    public OutputHelper(string result, string score1, string score2, string score3, string score4)
    {
      this.result = result;
      Score1 = score1;
      Score2 = score2;
      Score3 = score3;
      Score4 = score4;
    }

    public string result { get; set; }
    public string Score1 { get; set; }
    public string Score2 { get; set; }
    public string Score3 { get; set; }
    public string Score4 { get; set; }
  }
}