using Microsoft.ML.Runtime.Api;

namespace MyMaster
{
  public class Master
  {
    [Column("3")] public float AlgoScore;

    [Column("5")] public float CliScore;

    [Column("4")] public float CodingScore;

    [Column("1")] public float DesignScore;

    [Column("7")] public float HardwareScore;

    [Column("0")] public float Label;

    [Column("2")] public float MathScore;

    [Column("6")] public float NetworkingScore;

    public Master(float label, float designScore, float mathScore, float algoScore, float codingScore, float cliScore,
      float networkingScore, float hardwareScore)
    {
      Label = label;
      DesignScore = designScore;
      MathScore = mathScore;
      AlgoScore = algoScore;
      CodingScore = codingScore;
      CliScore = cliScore;
      NetworkingScore = networkingScore;
      HardwareScore = hardwareScore;
    }
  }
}