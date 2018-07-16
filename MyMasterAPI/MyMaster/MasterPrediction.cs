using Microsoft.ML.Runtime.Api;

namespace MyMaster
{
  public class MasterPrediction
  {
    [ColumnName("Score")] public float[] Score;
  }
}